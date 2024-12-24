package com.ibas.brta.vehims.acs.controller;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import com.ibas.brta.vehims.acs.model.PaymentDetails;
import com.ibas.brta.vehims.acs.model.PaymentInfo;
import com.ibas.brta.vehims.acs.model.TransactionInfo;
import com.ibas.brta.vehims.acs.payload.request.PaymentDetailsRequest;
import com.ibas.brta.vehims.acs.payload.request.PaymentInitiateOtcRequest;
import com.ibas.brta.vehims.acs.payload.request.PaymentInitiateRequest;
import com.ibas.brta.vehims.acs.payload.request.PaymentUpdateRequest;
import com.ibas.brta.vehims.acs.payload.response.LoginResponse;
import com.ibas.brta.vehims.acs.payload.response.PaymentDetailsResponse;
import com.ibas.brta.vehims.acs.payload.response.PaymentInitiateOtcResponse;
import com.ibas.brta.vehims.acs.payload.response.PaymentInitiateResponse;
import com.ibas.brta.vehims.acs.payload.response.PaymentResponse;
import com.ibas.brta.vehims.acs.payload.response.PaymentUrl;
import com.ibas.brta.vehims.acs.payload.response.ResponseCode;
import com.ibas.brta.vehims.acs.repository.PaymentDetailsRepository;
import com.ibas.brta.vehims.acs.service.PaymentInfoService;
import com.ibas.brta.vehims.acs.service.TransactionInfoService;
import com.ibas.brta.vehims.configurations.model.ServiceEntity;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.configurations.repository.ServiceRepository;
import com.ibas.brta.vehims.drivingLicense.model.DLServiceRequest;
import com.ibas.brta.vehims.drivingLicense.model.LearnerLicense;
import com.ibas.brta.vehims.drivingLicense.payload.request.LearnerLicenseRequest;
import com.ibas.brta.vehims.drivingLicense.payload.response.LearnerLicenseResponse;
import com.ibas.brta.vehims.drivingLicense.repository.DLServiceRequestRepository;
import com.ibas.brta.vehims.drivingLicense.repository.LearnerLicenseRepository;
import com.ibas.brta.vehims.drivingLicense.service.DLServiceRequestService;
import com.ibas.brta.vehims.drivingLicense.service.DrivingLicenseService;
import com.ibas.brta.vehims.drivingLicense.service.LearnerLicenseService;
import com.ibas.brta.vehims.exception.ResourceNotFoundException;
import com.ibas.brta.vehims.projection.StatusProjection;
import com.ibas.brta.vehims.security.CurrentUser;
import com.ibas.brta.vehims.security.UserPrincipal;
import com.ibas.brta.vehims.util.Utils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Credentials;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author ashshakur@ibas.gov.bd
 */

@Slf4j
@RestController
@RequestMapping("/api/bsp/acs/")
@CrossOrigin(origins = "*")
@Validated
public class PaymentController {

    public static final Logger logger = org.slf4j.LoggerFactory.getLogger(PaymentController.class);

    @Value("${acs.base_url}")
    private String acsBaseUrl;

    @Value("${acs.username}")
    private String acsUsername;

    @Value("${acs.password}")
    private String acsPassword;

    @Value("${acs.loginun}")
    private String acsLoginUserName;

    @Value("${acs.loginpw}")
    private String acsLoginPassword;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private TransactionInfoService transactionInfoService;

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private DLServiceRequestRepository dlServiceRequestRepository;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private DrivingLicenseService drivingLicenseService;

    @Autowired
    private LearnerLicenseService learnerLicenseService;

    @Autowired
    private LearnerLicenseRepository learnerLicenseRepository;

    @RequestMapping(value = "v1/payment/initiate/online", produces = "application/json", method = RequestMethod.POST)
    ResponseEntity<?> initiatePaymentV1(@CurrentUser UserPrincipal currentUser,
            @Valid @org.springframework.web.bind.annotation.RequestBody PaymentInitiateRequest paymentInitiateRequest) {

        logger.error("Trying to to access payment initiate from ACS.");

        UUID guid = UUID.randomUUID();
        String transactionId = currentUser.getId() + "" + Utils.datetoReportString(new Date()) + ""
                + guid.toString().replace("-", "").substring(0, 4);

        PaymentInitiateResponse response = getPaymentInitiateDataSetFromAcs(paymentInitiateRequest,
                "clients/payment/initiate");

        String url = "";

        if (response.getResponsecode().equals("0")) {
            url = response.getResponseurl() + "?transaction_id=" + response.getTransaction_id();

            TransactionInfo _transactionInfo = new TransactionInfo();

            _transactionInfo.setBearerToken(response.getBearertoken());
            _transactionInfo.setTransactionId(response.getTransaction_id());
            _transactionInfo.setServiceRequestNo(paymentInitiateRequest.getServiceRequestNo());
            ServiceEntity serviceEntity = serviceRepository.findByServiceCode(paymentInitiateRequest.getServiceCode());

            if (serviceEntity != null) {
                _transactionInfo.setServiceId(serviceEntity.getId());
            }

            // SAVE RESPONSE to DB for future payment confirmaiton
            _transactionInfo = transactionInfoService.save(_transactionInfo);

            logger.error("Transaction Reference saved:" + url);

        }

        logger.error("Response:-->" + url);
        return new ResponseEntity<PaymentUrl>(new PaymentUrl(url), HttpStatus.OK);
    }

    @RequestMapping(value = "v1/payment/initiate/otc", produces = "application/json", method = RequestMethod.POST)
    ResponseEntity<?> initiateOtcPaymentV1(@CurrentUser UserPrincipal currentUser,
            @Valid @org.springframework.web.bind.annotation.RequestBody PaymentInitiateOtcRequest paymentInitiateRequest) {

        logger.error("Trying to initiate OTC payment.");

        // GET Office Code for the bin holder

        String _bin = paymentInitiateRequest.getBin();

        // TODO: Organization Code:-

        String _organizationCode = "";

        logger.error("Organization code:-->" + _organizationCode);

        paymentInitiateRequest.setOrganizationcode(_organizationCode);

        logger.error("Payment Object to send to ACS:" + paymentInitiateRequest.toString());

        PaymentInitiateOtcResponse response = getPaymentInitiateOtcDataSetFromAcs(paymentInitiateRequest,
                "clients/payment/initiateOTC");

        return new ResponseEntity<PaymentInitiateOtcResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "v1/payment/details/{paymentid}", produces = "application/json", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ROLE_BIN')")
    ResponseEntity<PaymentDetailsResponse> getPaymentDetails(@CurrentUser UserPrincipal currentUser,
            @PathVariable("paymentid") String paymentid) {

        logger.error("Trying to access payment details: ");

        PaymentDetailsRequest detailRequest = new PaymentDetailsRequest();
        detailRequest.setPaymentid(paymentid);
        detailRequest.setChallanno("");

        PaymentDetailsResponse response = getPaymentDetailsFromACS(detailRequest,
                "clients/payment/details");

        // Before returning the details -- Save if not available

        Optional<PaymentDetails> _paymentDetails = paymentDetailsRepository.findByPaymentId(paymentid);
        if (_paymentDetails.isPresent()) {
            logger.error("// Payment details info already saved. UPDATE");

            PaymentDetails paymentDetails = _paymentDetails.get();

            logger.error("// Payment details info already saved. UPDATE:" + paymentDetails.getPeriodKey());

            try {
                paymentDetails.setAddress(response.getAddress());
                paymentDetails.setBankName(response.getBank_name());
                paymentDetails.setBin(response.getBin());
                paymentDetails.setTin(response.getTin());
                paymentDetails.setChallanNo(response.getChallan_no());
                paymentDetails.setClientName(response.getClient_name());
                paymentDetails.setEmail(response.getEmail());
                paymentDetails.setEntryDate(response.getEntry_date());
                paymentDetails.setMobileNo(response.getMobile_no());
                paymentDetails.setPaidAmount(response.getPaidamount());
                paymentDetails.setPaymentDate(response.getPayment_date());
                paymentDetails.setPaymentId(response.getPayment_id());
                paymentDetails.setRoutingNo(response.getRouting_no());
                paymentDetails.setSubmissionId(response.getSubmissionId());
                paymentDetails.setUsedFlag(response.getUsed_flag());

            } catch (Exception ex) {

                logger.error("// Failed to UPDATE payment details." + paymentid);
            }

            paymentDetails = paymentDetailsRepository.save(paymentDetails);

            logger.error(
                    "Updated payment details with period and bank information:" + paymentDetails.getPaymentDate() + "->"
                            + paymentDetails.getPeriodKey());

        } else {
            PaymentDetails paymentDetails = new PaymentDetails();
            try {
                logger.error("// Payment details not FOUND:" + paymentid);

                paymentDetails.setAddress(response.getAddress());
                paymentDetails.setBankName(response.getBank_name());
                paymentDetails.setBin(response.getBin());
                paymentDetails.setTin(response.getTin());
                paymentDetails.setChallanNo(response.getChallan_no());
                paymentDetails.setClientName(response.getClient_name());
                paymentDetails.setEmail(response.getEmail());
                paymentDetails.setEntryDate(response.getEntry_date());
                paymentDetails.setMobileNo(response.getMobile_no());
                paymentDetails.setPaidAmount(response.getPaidamount());
                paymentDetails.setPaymentDate(response.getPayment_date());
                paymentDetails.setPaymentId(response.getPayment_id());
                paymentDetails.setRoutingNo(response.getRouting_no());
                paymentDetails.setSubmissionId(response.getSubmissionId());
                paymentDetails.setUsedFlag(response.getUsed_flag());

            } catch (Exception ex) {

                logger.error("//Failed to save payment details." + paymentid);
            }

            paymentDetails = paymentDetailsRepository.save(paymentDetails);

            logger.error("// Payment details is saved: " + paymentDetails.getId());
        }

        return new ResponseEntity<PaymentDetailsResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "v1/payment/list/{bin}", produces = "application/json", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ROLE_BIN')")
    List<PaymentDetails> getPaymentDetailsByBin(@CurrentUser UserPrincipal currentUser,
            @PathVariable("tin") String tin) {

        logger.error("Trying to get list of payment details for the tin: " + tin);

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findByTinEquals(tin);

        return paymentDetailsList;
    }

    /**
     * Returns information for the bin.
     */
    @RequestMapping(value = "/v1/payment/info", produces = "application/json", method = RequestMethod.GET)
    ResponseEntity<PaymentResponse> getPaymentInfo(@CurrentUser UserPrincipal currentUser,
            @RequestParam Map<String, String> queryParams) {

        logger.error("Get payment info from ACS.");

        String transaction_id = "";
        String challan_no = "";
        String clientname = "";

        String paymentid = "";
        String paidamount = "";

        // transaction_id=ipiX2kCLh9rvKag5XnnOlG8g8KyN8P4jGgu12cWLYKDwGDfm&challan_no=2324-0000002646&clientname=AZAN+COLLECTION&paidamount=5000&paymentid=2m9d113s32R

        PaymentResponse paymentResponse = new PaymentResponse();

        try {
            transaction_id = queryParams.get("transaction_id");
            paymentResponse.setTransaction_id(transaction_id);
            logger.error("Transaction ID:" + transaction_id);

        } catch (NullPointerException npex) {
            npex.printStackTrace();
        }

        // find token info by transaction
        TransactionInfo _transactionInfo = transactionInfoService.findByTransactionId(transaction_id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction ID", "id", ""));
        String bearerToken = _transactionInfo.getBearerToken();

        PaymentInfo _paymentInfo = new PaymentInfo();

        _paymentInfo.setBearerToken(bearerToken);
        _paymentInfo.setTransactionId(transaction_id);

        try {
            challan_no = queryParams.get("challan_no");
            paymentResponse.setChallan_no(challan_no);
            _paymentInfo.setChallanNo(challan_no);
            logger.error("challan_no:" + challan_no);

        } catch (NullPointerException npex) {
            logger.error("challan_no:" + challan_no);
        }

        try {
            clientname = queryParams.get("clientname");
            paymentResponse.setClientname(clientname);
            _paymentInfo.setClientName(clientname);
            logger.error("clientname:" + clientname);

        } catch (NullPointerException npex) {

            logger.error("Client name null.");
        }

        try {
            paidamount = queryParams.get("paidamount");
            paymentResponse.setPaidamount(paidamount);
            _paymentInfo.setPaidAmount(paidamount);
            logger.error("paidamount:" + paidamount);

        } catch (NullPointerException npex) {
        }

        try {
            paymentid = queryParams.get("paymentid");
            paymentResponse.setPaymentid(paymentid);
            _paymentInfo.setPaymentId(paymentid);
            logger.error("paymentid:" + paymentid);

        } catch (NullPointerException npex) {
            logger.error("paymentid:" + npex.getMessage());

        }

        // logger.error("Getting payment confirmation:");
        // // CALL Confirmation API
        String endpt = "/clients/payment/confirmation";

        String response = getPaymentConfirFromAcs(bearerToken, paymentResponse, endpt);

        Gson gson = new Gson();

        ResponseCode responseCode = gson.fromJson(response, ResponseCode.class);

        logger.error("//UPDATE payment Info in local DB");
        Optional<PaymentInfo> paymentExist = paymentInfoService.findByPaymentId(paymentid);

        if (!paymentExist.isPresent()) {
            _paymentInfo = paymentInfoService.save(_paymentInfo);
        }

        // paymentInfoService.save(_paymentInfo);

        Optional<TransactionInfo> transactionInfo = transactionInfoService
                .findByTransactionId(_paymentInfo.getTransactionId());

        if (transactionInfo.isPresent()) {

            Optional<ServiceEntity> serviceEntity = serviceRepository.findById(transactionInfo.get().getServiceId());

            if (serviceEntity.isPresent()) {
                paymentResponse.setServiceRequestNo(transactionInfo.get().getServiceRequestNo());
                paymentResponse.setServiceId(transactionInfo.get().getServiceId());
                paymentResponse.setServiceCode(serviceEntity.get().getServiceCode());

                if (serviceEntity.get().getServiceCode().equals("before_driving_skills_test_fees")
                        || serviceEntity.get().getServiceCode().equals("after_driving_skills_test_fees")) {

                    DLServiceRequest dlServiceRequest = dlServiceRequestRepository
                            .findByServiceRequestNo(transactionInfo.get().getServiceRequestNo());

                    if (dlServiceRequest != null) {
                        if (serviceEntity.get().getServiceCode().equals("before_driving_skills_test_fees")) {

                            StatusProjection statusPending = commonRepository
                                    .getStatusByStatusCodeOrId("dl_app_pending");
                            dlServiceRequest.setApplicationStatusId(statusPending.getId());
                            dlServiceRequest.setApplicationDate(LocalDateTime.now());
                            dlServiceRequest.setIsLearnerFeePaid(true);

                            LearnerLicenseResponse existLearner = learnerLicenseService
                                    .getDataByServiceRequestId(dlServiceRequest.getId());

                            if (existLearner != null) {

                                LearnerLicenseRequest learnerLicenseRequest = new LearnerLicenseRequest();
                                BeanUtils.copyProperties(existLearner, learnerLicenseRequest);

                                learnerLicenseRequest.setDlServiceRequestId(dlServiceRequest.getId());
                                learnerLicenseRequest.setLearnerNumber(Utils.generateLearnerNumber());
                                learnerLicenseRequest.setIssueDate(LocalDateTime.now());
                                learnerLicenseRequest.setExamDate(LocalDateTime.now().plusMonths(3));
                                learnerLicenseRequest.setExpireDate(LocalDateTime.now().plusMonths(6));

                                LearnerLicense learnerLicense = learnerLicenseRepository
                                        .findByDlServiceRequestId(dlServiceRequest.getId());

                                Integer totalLearner = learnerLicenseRepository.countByExamDateAndExamVenueId(
                                        learnerLicenseRequest.getExamDate(),
                                        learnerLicense.getExamVenueId());

                                if (totalLearner <= 600) {

                                    LearnerLicenseResponse learnerLicenseResponse = learnerLicenseService
                                            .getDataByExamDateAndVenue(
                                                    learnerLicenseRequest.getExamDate(),
                                                    learnerLicense.getExamVenueId());

                                    if (learnerLicenseResponse != null) {
                                        learnerLicenseRequest.setRollNo(learnerLicenseResponse.getRollNo() + 1);
                                    } else {
                                        learnerLicenseRequest.setRollNo(1);
                                    }
                                } else {

                                    learnerLicenseRequest.setExamDate(learnerLicenseRequest.getExamDate().plusDays(1));
                                    learnerLicenseRequest
                                            .setExpireDate(learnerLicenseRequest.getExpireDate().plusDays(1));

                                    LearnerLicenseResponse learnerLicenseResponse = learnerLicenseService
                                            .getDataByExamDateAndVenue(
                                                    learnerLicenseRequest.getExamDate(),
                                                    learnerLicense.getExamVenueId());

                                    if (learnerLicenseResponse != null) {
                                        learnerLicenseRequest.setRollNo(learnerLicenseResponse.getRollNo() + 1);
                                    } else {
                                        learnerLicenseRequest.setRollNo(1);
                                    }
                                }
                                learnerLicenseRequest.setIsPaid(true);

                                learnerLicenseService.updateData(existLearner.getId(), learnerLicenseRequest);

                            }

                            // learnerLicenseService.storeOrUpdateLearnerData(learnerLicenseRequest);
                        }

                        if (serviceEntity.get().getServiceCode().equals("after_driving_skills_test_fees")) {

                            StatusProjection statusPending = commonRepository
                                    .getStatusByStatusCodeOrId("dl_app_final_submitted");
                            dlServiceRequest.setApplicationStatusId(statusPending.getId());
                            dlServiceRequest.setIsLicenseFeePaid(true);
                        }

                        dlServiceRequestRepository.save(dlServiceRequest);
                    }
                }
            }
        }

        return new ResponseEntity<PaymentResponse>(paymentResponse, HttpStatus.OK);

    }

    /**
     * Initiate payment with payment request info from the device.
     *
     * @param paymentRequest Payment Request DTO: paymentid, paidamount,
     *                       paymenttype, bin, mobile
     * @param endpoint
     * @return
     */

    private PaymentInitiateResponse getPaymentInitiateDataSetFromAcs(PaymentInitiateRequest paymentRequest,
            String endpoint) {
        // logger.error("Pulling data from ACS.");
        try {

            LoginResponse loginResponse = getAccessTokenFromAcs();

            MediaType mediaType = MediaType.parse("application/json");

            Gson requestGson = new Gson();

            String jString = requestGson.toJson(paymentRequest);

             logger.error("Sending json-->" + jString);

            RequestBody body = RequestBody.create(jString, mediaType);

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(this.acsBaseUrl + endpoint)
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer " + loginResponse.getAccess_token())
                    .addHeader("Content-Type", "application/json")
                    .build();

            okhttp3.Response response = client.newCall(request).execute();

            Gson gson = new Gson();

            jString = response.body().string();

            logger.error("Received jSon:-->" + jString);

            PaymentInitiateResponse piresponse = gson.fromJson(jString, PaymentInitiateResponse.class);

            piresponse.setBearertoken(loginResponse.getAccess_token());

            // return response.body().string(); // response.peekBody(16000).string();
            logger.error("Response Redirect URL:-" + piresponse.getResponseurl());

            return piresponse;

        } catch (IOException ioException) {
            logger.error("Failed to read get data from ACS:", ioException.getMessage());
        }
        return null;
    }

    /**
     * Initiate payment with payment request OTC
     *
     * @param paymentRequest Payment Request DTO: paymentid, paidamount,
     *                       paymenttype, bin, mobile
     * @param endpoint
     * @return
     */

    private PaymentInitiateOtcResponse getPaymentInitiateOtcDataSetFromAcs(PaymentInitiateOtcRequest paymentRequest,
            String endpoint) {
        // logger.error("Pulling data from ACS --OTC.");
        try {

            LoginResponse loginResponse = getAccessTokenFromAcs();

            // logger.error("ACS Token:" + loginResponse.getAccess_token());

            MediaType mediaType = MediaType.parse("application/json");

            Gson requestGson = new Gson();

            String jString = requestGson.toJson(paymentRequest);

            // logger.error("Sending json-->" + jString);

            RequestBody body = RequestBody.create(jString, mediaType);

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(this.acsBaseUrl + endpoint)
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer " + loginResponse.getAccess_token())
                    .addHeader("Content-Type", "application/json")
                    .build();

            okhttp3.Response response = client.newCall(request).execute();

            Gson gson = new Gson();

            jString = response.body().string();

            logger.error("Received jSon:-->" + jString);

            PaymentInitiateOtcResponse piresponse = gson.fromJson(jString, PaymentInitiateOtcResponse.class);

            return piresponse;

        } catch (IOException ioException) {
            logger.error("Failed to read get OTC data from ACS:", ioException.getMessage());
        }
        return null;
    }

    private String getPaymentConfirFromAcs(String bearerToken, PaymentResponse paymentResponse,
            String endpoint) {
        // logger.error("Pulling data from ACS for payment confirmation.");
        try {

            LoginResponse loginResponse = getAccessTokenFromAcs();

            // logger.error("ACS Token:" + loginResponse.getAccess_token());

            MediaType mediaType = MediaType.parse("application/json");

            RequestBody body = RequestBody.create("", mediaType);

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(this.acsBaseUrl + endpoint + "?access_token=" + bearerToken + "&challan_no="
                            + paymentResponse.getChallan_no() + "&paymentid=" + paymentResponse.getPaymentid()
                            + "&paidamount=" + paymentResponse.getPaidamount() + "&transaction_id="
                            + paymentResponse.getTransaction_id())
                    // .method("GET", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + loginResponse.getAccess_token())
                    .build();

            okhttp3.Response response = client.newCall(request).execute();

            return response.body().string();

        } catch (IOException ioException) {
            // ioException.printStackTrace();
            logger.error("Failed to get payment confirmation from ACS:", ioException.getMessage());
        }
        return null;
    }

    /**
     * Get ACCESS TOKEN from ACS to be sent with subsequent API call.
     *
     * @return
     */

    public LoginResponse getAccessTokenFromAcs() {
        LoginResponse loginResponse = new LoginResponse();

        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

            RequestBody body = RequestBody.create(
                    "username=" + this.acsLoginUserName + "&password=" + this.acsLoginPassword + "&grant_type=password",
                    mediaType);

            String credential = Credentials.basic(acsUsername, acsPassword);

            // logger.error("URL:-->" + acsBaseUrl + "token");

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(acsBaseUrl + "token")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Authorization", credential)
                    .build();

            okhttp3.Response response = client.newCall(request).execute();

            Gson gson = new Gson();

            String jString = response.body().string();

            // logger.error("Response from auth:-->" + jString);

            loginResponse = gson.fromJson(jString, LoginResponse.class);
            // logger.error("Response from auth:-->" + loginResponse.getAccess_token());

        } catch (IOException ioException) {
            // ioException.printStackTrace();
            logger.error("Failed to get access token:", ioException.getMessage());
        }

        return loginResponse;
    }

    /**
     * Initiate payment with payment request info from the device.
     *
     * @param paymentRequest Payment Request DTO: paymentid, paidamount,
     *                       paymenttype, bin, mobile
     * @param endpoint
     * @return
     */

    public PaymentDetailsResponse getPaymentDetailsFromACS(PaymentDetailsRequest paymentRequest,
            String endpoint) {
        // logger.error("Pulling data from ACS for payment details.");
        try {

            LoginResponse loginResponse = getAccessTokenFromAcs();

            // MediaType mediaType = MediaType.parse("application/json");

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            // challan_no="+ paymentRequest.getChallanno() + "&
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(this.acsBaseUrl + endpoint + "?paymentid=" + paymentRequest.getPaymentid())
                    // .method("GET", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + loginResponse.getAccess_token())
                    .build();

            okhttp3.Response response = client.newCall(request).execute();

            Gson gson = new Gson();

            String jString = response.body().string();

            PaymentDetailsResponse detailResponse = gson.fromJson(jString, PaymentDetailsResponse.class);

            return detailResponse;
        } catch (IOException ioException) {
            // ioException.printStackTrace();
            logger.error("Failed to get payment details from ACS:", ioException.getMessage());
        }
        return new PaymentDetailsResponse();
    }

}

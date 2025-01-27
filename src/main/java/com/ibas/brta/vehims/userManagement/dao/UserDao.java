package com.ibas.brta.vehims.userManagement.dao;

import com.ibas.brta.vehims.serviceFees.model.VehicleServiceFees;
import com.ibas.brta.vehims.userManagement.model.UserOfficeRole;
import com.ibas.brta.vehims.userManagement.payload.response.UserOfficeRoleResponse;
import com.ibas.brta.vehims.util.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    EntityManager entityManager;

    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<UserOfficeRoleResponse> getUserOfficeRoles(Long userId) {
        // Construct the query
        StringBuilder querySt = new StringBuilder();
        querySt.append("SELECT suor.user_id, suor.org_id, suor.role_id, " +
                "org.name_en, org.name_bn, role.name_en, role.name_bn " +
                "FROM s_user_organization_roles suor " +
                "JOIN c_organizations org ON suor.org_id = org.org_id " +
                "JOIN u_roles role ON suor.role_id = role.role_id " +
                "WHERE suor.user_id = :userId");

        // Execute the query
        Query query = entityManager.createNativeQuery(querySt.toString(), UserOfficeRoleResponse.class);
        query.setParameter("userId", userId);

        // Get the raw result list
        return query.getResultList();
    }

    public List<UserOfficeRoleResponse> getUserOfficeRoles2(Long userId) {
        // Construct the query
        StringBuilder querySt = new StringBuilder();
        querySt.append("SELECT suor.user_id, suor.org_id, suor.role_id, " +
                "org.name_en, org.name_bn, role.name_en, role.name_bn " +
                "FROM s_user_organization_roles suor " +
                "JOIN c_organizations org ON suor.org_id = org.org_id " +
                "JOIN u_roles role ON suor.role_id = role.role_id " +
                "WHERE suor.user_id = :userId");

        // Execute the query
        Query query = entityManager.createNativeQuery(querySt.toString());
        query.setParameter("userId", userId);

        // Get the raw result list
        List<Object[]> resultList = query.getResultList();

        List<UserOfficeRoleResponse> userOfficeRoles = new ArrayList<>();
        for (Object[] row : resultList) {
            Long userIdValue = (row[0] instanceof BigInteger) ? ((BigInteger) row[0]).longValue() : (Long) row[0];
            Long orgIdValue = (row[1] instanceof BigInteger) ? ((BigInteger) row[1]).longValue() : (Long) row[1];
            Long roleIdValue = (row[2] instanceof BigInteger) ? ((BigInteger) row[2]).longValue() : (Long) row[2];

            UserOfficeRoleResponse response = new UserOfficeRoleResponse(
                    userIdValue, // userId
                    orgIdValue, // orgId
                    roleIdValue, // roleId
                    (String) row[3], // orgNameEn
                    (String) row[4], // orgNameBn
                    (String) row[5], // roleNameEn
                    (String) row[6] // roleNameBn
            );
            userOfficeRoles.add(response);
        }

        return userOfficeRoles;
    }
}

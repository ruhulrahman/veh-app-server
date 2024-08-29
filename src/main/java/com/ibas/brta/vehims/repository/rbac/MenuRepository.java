package com.ibas.brta.vehims.repository.rbac;

import com.ibas.brta.vehims.projection.MenuProjection;
import com.ibas.brta.vehims.model.rbac.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * To interact with the database to query, save, update, or delete menu-related data.
 * @author ashshakur.rahaman
 * @version 1.0 Initial version
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query(value = "SELECT m.menu_id AS menuId, m.menu_name_en AS menuNameEn, m.menu_name_bn as menuNameBn, m.menu_url AS menuUrl, m.parent_menu_id AS parentMenuId "
            + " FROM  m_role_menu mr inner join r_menu m on mr.menu_id = m.menu_id where mr.role_id=:roleId order by m.menu_id asc ", nativeQuery = true)
    List<MenuProjection> findMenuByRoleId(Long roleId);
}

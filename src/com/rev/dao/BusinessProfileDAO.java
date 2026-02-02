package com.rev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rev.model.BusinessProfile;
import com.rev.util.DBUtil;

public class BusinessProfileDAO {

    public BusinessProfile getByUserId(int userId) {

        String sql = """
                SELECT industry, contact_info, address,
                       business_hours, external_links, products_services
                FROM business_profiles
                WHERE user_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                BusinessProfile profile = new BusinessProfile();
                profile.setUserId(userId);
                profile.setIndustry(rs.getString("industry"));
                profile.setContactInfo(rs.getString("contact_info"));
                profile.setAddress(rs.getString("address"));
                profile.setBusinessHours(rs.getString("business_hours"));
                profile.setExternalLinks(rs.getString("external_links"));
                profile.setProductsServices(rs.getString("products_services"));
                return profile;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean update(BusinessProfile profile) {

        String sql = """
                UPDATE business_profiles
                SET industry = ?, contact_info = ?, address = ?,
                    business_hours = ?, external_links = ?, products_services = ?
                WHERE user_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, profile.getIndustry());
            ps.setString(2, profile.getContactInfo());
            ps.setString(3, profile.getAddress());
            ps.setString(4, profile.getBusinessHours());
            ps.setString(5, profile.getExternalLinks());
            ps.setString(6, profile.getProductsServices());
            ps.setInt(7, profile.getUserId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createEmpty(int userId) {

        String sql = """
                INSERT INTO business_profiles (user_id)
                VALUES (?)
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

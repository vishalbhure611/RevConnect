package com.rev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rev.model.CreatorProfile;
import com.rev.util.DBUtil;

public class CreatorProfileDAO {

    public CreatorProfile getByUserId(int userId) {

        String sql = """
                SELECT category, contact_info, external_links
                FROM creator_profiles
                WHERE user_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                CreatorProfile profile = new CreatorProfile();
                profile.setUserId(userId);
                profile.setCategory(rs.getString("category"));
                profile.setContactInfo(rs.getString("contact_info"));
                profile.setExternalLinks(rs.getString("external_links"));
                return profile;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean update(CreatorProfile profile) {

        String sql = """
                UPDATE creator_profiles
                SET category = ?, contact_info = ?, external_links = ?
                WHERE user_id = ?
                """;

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, profile.getCategory());
            ps.setString(2, profile.getContactInfo());
            ps.setString(3, profile.getExternalLinks());
            ps.setInt(4, profile.getUserId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createEmpty(int userId) {

        String sql = """
                INSERT INTO creator_profiles (user_id)
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

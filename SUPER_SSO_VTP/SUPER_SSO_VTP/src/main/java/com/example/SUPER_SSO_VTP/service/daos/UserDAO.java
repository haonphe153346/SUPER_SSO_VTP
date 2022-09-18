package com.example.SUPER_SSO_VTP.service.daos;

import com.example.SUPER_SSO_VTP.base.BaseDAO;
import com.example.SUPER_SSO_VTP.exception.VtException;
import com.example.SUPER_SSO_VTP.model.response.ModuleData;
import com.example.SUPER_SSO_VTP.model.response.UserData;
import com.example.SUPER_SSO_VTP.util.Utils;
import oracle.jdbc.internal.OracleTypes;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class UserDAO extends BaseDAO {
    @Autowired
    @Qualifier("coreFactory")
    SessionFactory sessionFactory;


    public Map<String, Object> getUserInfo(String username, String maBuuCuc) throws Exception {
        Query query = getSession(sessionFactory)
                .createSQLQuery("SELECT B.USERID, B.DN_USERID, EMPLOYEE_GROUP_ID, B.FIRSTNAME, B.LASTNAME, B.USERNAME, B.TELEPHONE, B.MA_CHUCDANH, B.MA_BUUCUC, EMAIL, DISPLAYNAME" +
                        ", PASSWORDFORMAT, PASSWORDSALT, VTP.GET_DEPT_CODE(MA_BUUCUC) CHI_NHANH, EMPLOYEECODE" +
                        " FROM ERP_HR.HR_EMPLOYEE A, VTP.SUSERS B WHERE A.USERNAME =  ? AND A.USERNAME = B.USERNAME AND MA_BUUCUC = ?")
                .addScalar("USERID", new LongType())
                .addScalar("DN_USERID", new LongType())
                .addScalar("EMPLOYEE_GROUP_ID", new LongType())
                .addScalar("FIRSTNAME", new StringType())
                .addScalar("LASTNAME", new StringType())
                .addScalar("USERNAME", new StringType())
                .addScalar("TELEPHONE", new StringType())
                .addScalar("EMAIL", new StringType())
                .addScalar("MA_CHUCDANH", new StringType())
                .addScalar("MA_BUUCUC", new StringType())
                .addScalar("DISPLAYNAME", new StringType())
                .addScalar("PASSWORDFORMAT", new StringType())
                .addScalar("PASSWORDSALT", new StringType())
                .addScalar("CHI_NHANH", new StringType())
                .addScalar("EMPLOYEECODE", new StringType())
                .setParameter(0, username)
                .setParameter(1, maBuuCuc);
        List<Object[]> ls = query.list();
        Map<String, Object> result = new HashMap<>();
        if (!Utils.isNullOrEmpty(ls)) {
            Object[] args = ls.get(0);
            result.put("userid", args[0]);
            result.put("dn_userid", args[1]);
            result.put("employee_group_id", args[2]);
            result.put("firstname", args[3]);
            result.put("lastname", args[4]);
            result.put("username", args[5]);
            result.put("phone", args[6]);
            result.put("ma_chucdanh", args[7]);
            result.put("ma_buucuc", args[8]);
            result.put("email", args[9]);
            result.put("name", args[10]);
            result.put("PASSWORDFORMAT", args[11]);
            result.put("PASSWORDSALT", args[12]);
            result.put("don_vi", args[13]);
            result.put("manhanvien", args[14]);
        }
        return result;
    }

    public  Map<String, Map<String, Object>> test() throws Exception {
        Query query = getSession(sessionFactory)
                .createSQLQuery("SELECT B.USERID, B.DN_USERID, EMPLOYEE_GROUP_ID, B.FIRSTNAME, B.LASTNAME, B.USERNAME, B.TELEPHONE, B.MA_CHUCDANH, B.MA_BUUCUC, EMAIL, DISPLAYNAME\n" +
                        "                         PASSWORDFORMAT, PASSWORDSALT, VTP.GET_DEPT_CODE(MA_BUUCUC) CHI_NHANH, EMPLOYEECODE\n" +
                        "                         FROM ERP_HR.HR_EMPLOYEE A, VTP.SUSERS B where isdeleted = 'Y' and ROWNUM <= 6")
                .addScalar("USERID", new LongType())
                .addScalar("DN_USERID", new LongType())
                .addScalar("EMPLOYEE_GROUP_ID", new LongType())
                .addScalar("FIRSTNAME", new StringType())
                .addScalar("LASTNAME", new StringType())
                .addScalar("USERNAME", new StringType())
                .addScalar("TELEPHONE", new StringType())
                .addScalar("EMAIL", new StringType());
        List<Object[]> ls = query.list();
        Map<String, Object> result = new HashMap<>();
        Map<String, Map<String, Object>> rs2 = new HashMap<>();
        if (!Utils.isNullOrEmpty(ls)) {
        for (Object[] o : ls) {
            UserData userData = new UserData();
            result.put("userid", o[0]);
            result.put("dn_userid", o[1]);
            result.put("employee_group_id", o[2]);
            result.put("firstname", o[3]);
            result.put("lastname", o[4]);
            result.put("username", o[5]);
            result.put("phone", o[6]);
            rs2.put("userid", result);
        }
        }
        return rs2;
    }

    public Object[] test123(int rowStart, int rowEnd) throws Exception {
        Object[] objs = getListResult(sessionFactory, "HR_USER_PK.GET_HR_USER_Paging", Arrays.asList(rowStart,rowEnd), new int[]{OracleTypes.VARCHAR});
        String msg = (String) objs[0];
        if (msg != null && !msg.isEmpty()) {
            throw new VtException(200, msg);
        }
        return objs;
    }

    public void sendOtpViaSms(String phone) throws Exception {
        Object[] objs = getListResult(sessionFactory, "VTP.EVTP_OTP.SEND_OTP_VTMAN", Arrays.asList(phone), new int[]{OracleTypes.VARCHAR});
        String msg = (String) objs[0];
        if (msg != null && !msg.isEmpty()) {
            throw new VtException(200, msg);
        }
    }



    private boolean buildMenuChildren(List<ModuleData> lsModule, ModuleData module) {
        for (ModuleData tmp : lsModule) {
            if (module.getParentId().compareTo(tmp.getId()) == 0) {
                if (module.getType().compareTo(2L) == 0) {
                    tmp.getLsFunction().add(module);
                } else {
                    tmp.getLsChildren().add(module);
                }
                return true;
            } else {
                if (!tmp.getLsChildren().isEmpty()) {
                    boolean test = buildMenuChildren(tmp.getLsChildren(), module);
                    if (test) return true;
                }
            }
        }
        return false;
    }
    @Transactional
    public List<ModuleData> getAllOwnerModule(Long cusId) {
        List<ModuleData> lsModule = new ArrayList<>();
        try {
            ResultSet rs = getResultSet(sessionFactory,"ERP_WALLET.VTP_WALLET_WEB.GET_ALL_MODULE", Arrays.asList(cusId), OracleTypes.CURSOR);
            while (rs.next()) {
                ModuleData module = new ModuleData();
                module.setId(rs.getLong("ID"));
                module.setType(rs.getLong("TYPE"));
                module.setParentId(rs.getLong("PARENT_ID"));
                module.setCode(rs.getString("CODE"));
                module.setName(rs.getString("NAME"));
                module.setDescription(rs.getString("DESCRIPTION"));
                module.setUrl(rs.getString("URL"));
                module.setIcon(rs.getString("ICON"));
                if (module.getType().compareTo(2L) > 0) {
                    lsModule.add(module);
                } else {
                    buildMenuChildren(lsModule, module);
                }
            }
        } catch (Exception ex) {

        }
        return lsModule;
    }

}

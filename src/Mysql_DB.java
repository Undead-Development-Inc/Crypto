import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import jdk.jfr.ContentType;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.sql.*;
import java.util.Base64;

public class Mysql_DB extends Blockchain {


    public void ADD_blocks(int BlockID) throws SQLException, ClassNotFoundException, IOException {
        new Net().DB_NETWORK("ADD_Block", BlockID);
        return;
    }

    public void Transaction_update() throws SQLException, ClassNotFoundException, IOException {
        new Net().DB_NETWORK("ADD_Transaction", 0);
        return;
    }


    public void GET_blocks() throws SQLException, ClassNotFoundException, IOException {
        new Net().DB_NETWORK("GET_Block", 0);
        return;
    }

    public void GET_block_Transactions() throws SQLException, ClassNotFoundException, GeneralSecurityException, IOException {
        new Net().DB_NETWORK("Get_Transaction", 0);
        return;
    }


    public void CLR_DB_BLOCK() throws SQLException, ClassNotFoundException, IOException {
        new Net().DB_NETWORK("CLR_BLOCKS", 0);
        return;
    }

    public void CLR_DB_TRANS() throws SQLException, ClassNotFoundException, IOException {
        new Net().DB_NETWORK("CLR_TRA", 0);
        return;
    }

    public void ADD_BLOCKCHAIN() throws IOException, ClassNotFoundException {
        new Net().DB_NETWORK("ADD_BLOCKCHAIN", 0);
        return;
    }



}

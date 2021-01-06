import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import jdk.jfr.ContentType;

import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.sql.*;
import java.util.Base64;

public class Mysql_DB {

        public String DB_Hst = "localhost";
        public String DB_PORT = "3306";
        public String DB_USR = "root";
        public String DB_PSWD = "Booboo3903@";
        public String DB_DB = "root";

        public void ADD_blocks(Block block) throws SQLException, ClassNotFoundException {
            try {
                String sql = "INSERT INTO `block` (`BlockHash`,`PrevHash`, `BlockID`,`BlockReward`, `MerkleRoot`, `Difficulty`,`timestamp`) VALUES (?,?,?,?,?,?,?)";

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://" + DB_Hst + ":" + DB_PORT + "/" + DB_DB, DB_USR, DB_PSWD);
                PreparedStatement preparedStatement = con.prepareStatement(sql);

                preparedStatement.setString(1, block.blockHash);
                preparedStatement.setString(2, block.PrevHash);
                preparedStatement.setInt(3, Blockchain.BlockChain.lastIndexOf(block));
                preparedStatement.setFloat(4, 0);
                preparedStatement.setString(5, block.Merkleroot);
                preparedStatement.setInt(6, block.diff);
                preparedStatement.setString(7, block.timestamp);


                int rs1 = preparedStatement.executeUpdate();
                preparedStatement.close();
                System.out.println("Adding Blocks to DB!!!");
            }catch (Exception ex){
                System.out.println("ERROR POSSIBLE NO-CONNECTION");
            }
    }

    public void Transaction_update(Transaction transaction, Block block) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `Transactions` (`TransHash`,`BlockHash`, `Sender`, `Recpt`, `Value`, `BID`, `TV`, `M`, `Fees`, `Signature`) VALUES (?,?,?,?,?,?,?,?,?,?)";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://"+ DB_Hst + ":" + DB_PORT + "/" + DB_DB, DB_USR, DB_PSWD);
        PreparedStatement preparedStatement = con.prepareStatement(sql);


            preparedStatement.setString(1,transaction.transhash);
            preparedStatement.setString(2, block.blockHash);
            preparedStatement.setString(3, transaction.from_address);
            preparedStatement.setString(4,transaction.Recpt_address);
            preparedStatement.setFloat(5, transaction.value);
            preparedStatement.setInt(6, Blockchain.BlockChain.lastIndexOf(block));
            preparedStatement.setInt(7, transaction.verified);
            preparedStatement.setBoolean(8, transaction.ISmined);
            preparedStatement.setFloat(9, transaction.Fees);
            preparedStatement.setObject(10, transaction.Signature);



            Block_update(transaction, block);

        int rs1 = preparedStatement.executeUpdate();
        preparedStatement.close();

        System.out.println("Updating Transaction DB!!!");
        return;
    }

    public void Block_update(Transaction transaction, Block block) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE `block` SET `MerkleRoot` = ?";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://"+ DB_Hst + ":" + DB_PORT + "/" + DB_DB, DB_USR, DB_PSWD);
        PreparedStatement preparedStatement = con.prepareStatement(sql);

        preparedStatement.setString(1, block.Merkleroot);



        block.MR_HASHLIST.add(StringUtil.applySha256(transaction.transhash));
        block.Merkleroot = StringUtil.applySha256(block.Merkleroot + transaction.transhash);


        int rs1 = preparedStatement.executeUpdate();
        preparedStatement.close();


        return;
    }

    public void GET_blocks() throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM `block`";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://"+ DB_Hst + ":" + DB_PORT + "/" + DB_DB, DB_USR, DB_PSWD);
        Statement statement = con.createStatement();



        ResultSet rs1 = statement.executeQuery(sql);
        while(rs1.next()){
            int blockid = rs1.getInt("BlockID");
            String BlockHash = rs1.getString("BlockHash");
            String PrevHash = rs1.getString("PrevHash");
            String MerkleRoot = rs1.getString("MerkleRoot");
            int difficulty = rs1.getInt("Difficulty");
            System.out.println("FOUND BLOCK: "+ blockid + " :With Hash: "+ BlockHash + " : Previous Hash : "+PrevHash);
            Block block = new Block(PrevHash);
            block.blockHash = BlockHash;
            Blockchain.BlockChain.add(block);
        }
        statement.close();
        System.out.println("LOADING BLOCKS FROM DB!!!");

    }

    public void GET_block_Transactions() throws SQLException, ClassNotFoundException, GeneralSecurityException, IOException {

        String sql = "SELECT * FROM `transactions`";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://"+ DB_Hst + ":" + DB_PORT + "/" + DB_DB, DB_USR, DB_PSWD);
        Statement statement = con.createStatement();



        ResultSet rs1 = statement.executeQuery(sql);

        while(rs1.next()){
            String TransactionHash = rs1.getString("TransHash");
            String BlockHash = rs1.getString("BlockHash");
            String Sender = rs1.getString("Sender");
            String Recpt = rs1.getString("Recpt");
            int value = rs1.getInt("Value");
            int BlockID = rs1.getInt("BID");
            int Verified = rs1.getInt("TV");
            Boolean mined = rs1.getBoolean("M");
            Float Fees = rs1.getFloat("Fees");

            if(Blockchain.BlockChain.get(BlockID -1).blockHash.matches(BlockHash)){
                System.out.println("Matches: " + Blockchain.BlockChain.get(BlockID -1).blockHash + " : DB: "+ BlockHash);
            }

            Transaction transaction = new Transaction(Sender, Recpt, value);
            transaction.transhash = TransactionHash;
            transaction.verified = Verified;

            if(BlockID ==0){
                System.out.println("BAD TRANSACTION!!");
            }else {
                Blockchain.BlockChain.get(BlockID -1) .Transactions.add(transaction);
            }



        }
        statement.close();


    }



    public void CLR_DB_BLOCK() throws SQLException, ClassNotFoundException {

        String sql = "TRUNCATE block";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://"+ DB_Hst + ":" + DB_PORT + "/" + DB_DB, DB_USR, DB_PSWD);
        PreparedStatement preparedStatement = con.prepareStatement(sql);


        int rs1 = preparedStatement.executeUpdate();
        preparedStatement.close();
        System.out.println("REMOVING BLOCKS FROM DB!!!!");

    }

    public void CLR_DB_TRANS() throws SQLException, ClassNotFoundException {

        String sql = "TRUNCATE transactions";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://"+ DB_Hst + ":" + DB_PORT + "/" + DB_DB, DB_USR, DB_PSWD);
        PreparedStatement preparedStatement = con.prepareStatement(sql);


        int rs1 = preparedStatement.executeUpdate();
        preparedStatement.close();
        System.out.println("REMOVING TRANSACTIONS FROM DB!!!!");

    }


    public Boolean FIND_Transaction(Transaction transaction) throws SQLException, ClassNotFoundException, GeneralSecurityException, IOException {

        String sql = "SELECT * FROM `transactions`";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://"+ DB_Hst + ":" + DB_PORT + "/" + DB_DB, DB_USR, DB_PSWD);
        PreparedStatement preparedStatement = con.prepareStatement(sql);



        ResultSet rs1 = preparedStatement.executeQuery(sql);



        while(rs1.next()){
            String Hash = rs1.getString("TransHash");
            //System.out.println("TRANSACTION HASH IS: "+ Hash);
        }
        preparedStatement.close();
        return false;
    }

    public Boolean FIND_Block(Block block) throws SQLException, ClassNotFoundException, GeneralSecurityException, IOException {

        String sql = "SELECT * FROM `block`";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://"+ DB_Hst + ":" + DB_PORT + "/" + DB_DB, DB_USR, DB_PSWD);
        PreparedStatement preparedStatement = con.prepareStatement(sql);



        ResultSet rs1 = preparedStatement.executeQuery(sql);

        while(rs1.next()){

            String BlK_Hash = rs1.getString("BlockHash");

            if(block.blockHash.matches(BlK_Hash)){
                return true;
            }


        }
        preparedStatement.close();

        return false;
    }


}


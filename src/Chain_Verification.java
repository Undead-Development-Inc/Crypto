import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class Chain_Verification {
    public static int Diff = new Difficulty().difficulty();
    public static String prefixString = new String(new char[Diff]).replace('\0', '0');

    public void givenBlockchain_whenNewBlockAdded_thenSuccess(Block block) throws  ClassNotFoundException, IOException {
        try {

            Block newBlock = new Block(block.get_DATA(), block
                    .getBlockHash(), new Date().getTime());
            for(Transaction transaction : block.get_DATA()){
                transaction.BlockHash = block.getBlockHash();
            }
            newBlock.mineBlock(Diff);
            System.out.println("Hashing");
            assertTrue(newBlock.getBlockHash()
                    .substring(0, Diff)
                    .equals(prefixString));

            Blockchain.BlockChain.add(newBlock);
            System.out.println("BLOCK: " + Blockchain.BlockChain.lastIndexOf(block) + " Previous Hash: " + block.getPreviousHash());

            return;
        }catch (Exception ex){

        }
    }

    @Test
    public void givenBlockchain_whenValidated_thenSuccess() {
        try {
            boolean flag = true;
            for (int i = 0; i < Blockchain.BlockChain.size(); i++) {
                String previousHash = i == 0 ? "0"
                        : Blockchain.BlockChain.get(i - 1)
                        .getBlockHash();
                flag = Blockchain.BlockChain.get(i)
                        .getBlockHash()
                        .equals(Blockchain.BlockChain.get(i)
                                .calculateBlockHash())
                        && previousHash.equals(Blockchain.BlockChain.get(i)
                        .getPreviousHash())
                        && Blockchain.BlockChain.get(i)
                        .getBlockHash()
                        .substring(0, Diff)
                        .equals(prefixString);
                if (!flag)
                    break;
            }
            assertTrue(flag);
        }catch (Exception ex){

        }
    }


}

/**
 *
 * @author peter
 */

import bead_egy.ReadAndProcess;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import org.junit.*;


public class MyTests {
    
    @Test(expected = FileNotFoundException.class)
    public void noSuchFile() throws Exception{
        ReadAndProcess rap = new ReadAndProcess("asd.txt");
        rap.readFile();
    }
    
    @Test(expected = NoSuchElementException.class)
    public void emptyFile() throws Exception{
        ReadAndProcess rap = new ReadAndProcess("./test/test1.txt");
        rap.readFile();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void brokenFile() throws Exception{
        ReadAndProcess rap = new ReadAndProcess("./test/test2.txt");
        rap.readFile();
    }
    
    @Test
    public void onePuffancsOneDay(){
        ReadAndProcess rap = new ReadAndProcess("./test/test3.txt");
        try{
            rap.readFile();
            rap.plants.get(0).resetRadiation();
            rap.process();
            
            Assert.assertEquals(1, rap.plants.size());
            Assert.assertEquals(4, rap.plants.get(0).getSoil());
            Assert.assertEquals("alfa", rap.plants.get(0).getRadiation().getType());
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    @Test
    public void onePuffancsTenDays(){
        ReadAndProcess rap = new ReadAndProcess("./test/test4.txt");
        try{
            rap.readFile();
            rap.plants.get(0).resetRadiation();
            rap.process();
            
            Assert.assertEquals(0, rap.plants.size());
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    @Test
    public void oneParaOneDay(){
        ReadAndProcess rap = new ReadAndProcess("./test/test5.txt");
        try{
            rap.readFile();
            rap.plants.get(0).resetRadiation();
            rap.process();
            
            Assert.assertEquals(1, rap.plants.size());
            Assert.assertEquals(4,rap.plants.get(0).getSoil());
            Assert.assertEquals("nincs",rap.plants.get(0).getRadiation().getType());
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    @Test
    public void oneParaTenDays(){
        ReadAndProcess rap = new ReadAndProcess("./test/test6.txt");
        try{
            rap.readFile();
            rap.plants.get(0).resetRadiation();
            rap.process();
            
            Assert.assertEquals(0,rap.plants.size());
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    @Test
    public void oneDeltaOneDay(){
        ReadAndProcess rap = new ReadAndProcess("./test/test7.txt");
        try{
            rap.readFile();
            rap.plants.get(0).resetRadiation();
            rap.process();
            
            Assert.assertEquals(1, rap.plants.size());
            Assert.assertEquals(4, rap.plants.get(0).getSoil());
            Assert.assertEquals("delta", rap.plants.get(0).getRadiation().getType());
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    @Test
    public void oneDeltaTenDays(){
        ReadAndProcess rap = new ReadAndProcess("./test/test8.txt");
        try{
            rap.readFile();
            rap.plants.get(0).resetRadiation();
            rap.process();
            
            Assert.assertEquals(1, rap.plants.size());
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    @Test
    public void exampleData(){
        ReadAndProcess rap = new ReadAndProcess("./test/test9.txt");
        try{
            rap.readFile();
            rap.plants.get(0).resetRadiation();
            rap.process();
            
            Assert.assertEquals(1, rap.plants.size());
            Assert.assertEquals("Kopcos", rap.plants.get(0).getName());
            Assert.assertEquals(2, rap.plants.get(0).getSoil());
        }catch(Exception e){
            System.out.println(e);
        }
    }
}

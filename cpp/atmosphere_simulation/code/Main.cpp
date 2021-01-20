#include "Simulate.hpp"


bool CompareFiles(std::string file1, std::string file2){
    std::ifstream f1(file1);
    std::ifstream f2(file2);
    bool same = true;
    char c1,c2;
    float fl1,fl2;
    while(!f1.eof() && !f2.eof() && same){
        f1 >> c1;f1 >> fl1;
        f2 >> c2;f2 >> fl2;
        //std::cout<<c1<<" " << c2<<" - "<<fl1<<" "<<fl2<<std::endl;
        same = c1==c2 && fl1==fl2;
    }
    return same;
}

//#define NORMAL_MODE
#ifdef NORMAL_MODE

int main(){
    Simulate s("./tests/test2.txt");
    do{
        s.PrintCurrent();
        s.Next();
    } while(!s.End());
}

#else
#define CATCH_CONFIG_MAIN
#include "catch.hpp"

TEST_CASE("Too_few_arguments")
{
    try{
        Simulate s("./tests/test1.txt");
    } catch(Simulate::MyExceptions e){
        REQUIRE(e == Simulate::MyExceptions::TooFewArguments);
    }
}

TEST_CASE("Successfull_reading"){
    try{
        Simulate s("./tests/test2.txt");

        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next0.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next1.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next2.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next3.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next4.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next5.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next6.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next7.txt","result.txt"));
        
        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next8.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next9.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next10.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next11.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next12.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next13.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next14.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next15.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next16.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next17.txt","result.txt"));

        s.Next();
        s.WriteCurrent();
        REQUIRE(CompareFiles("./next/next18.txt","result.txt"));
        
        REQUIRE(s.End());
    } catch(Simulate::MyExceptions e){/*there should be no exception here*/}
}
#endif

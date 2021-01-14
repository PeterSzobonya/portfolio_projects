#pragma once

#include<string>
#include<fstream>
#include<sstream>
#include<iostream>

#include "MyElement.hpp"
#include "Condition.hpp"

class Simulate
{
    public:
        Simulate (const std::string &filename);

        void PrintCurrent();
        void Next();
        bool End();

        void WriteCurrent();

        enum MyExceptions{TooFewArguments};
    private:
        
        int startLayers;
        bool end;
        std::queue<std::unique_ptr<Condition>> conditions;
        std::vector<std::unique_ptr<MyElement>> layers;
};
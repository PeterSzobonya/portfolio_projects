#include "MyElement.hpp"

void Ozone::Rain(std::vector<std::unique_ptr<MyElement>> &e)
{
    e.push_back(std::move(std::make_unique<Ozone>(0)));
}

void Oxigen::Rain(std::vector<std::unique_ptr<MyElement>> &e)
{
    e.push_back(std::move(std::make_unique<Ozone>(thickness*0.5)));
    //e.push_back(std::move(std::make_unique<Oxigen>(thickness*0.5)));
    thickness = thickness*0.5;
}

void Carbondioxide::Rain(std::vector<std::unique_ptr<MyElement>> &e)
{
    e.push_back(std::move(std::make_unique<Carbondioxide>(0)));
}



void Ozone::Sun(std::vector<std::unique_ptr<MyElement>> &e)
{
    e.push_back(std::move(std::make_unique<Ozone>(0)));
}

void Oxigen::Sun(std::vector<std::unique_ptr<MyElement>> &e)
{
    e.push_back(std::move(std::make_unique<Ozone>(thickness*0.05)));
    //e.push_back(std::move(std::make_unique<Oxigen>(thickness*0.95)));
    thickness = thickness*0.95;
}

void Carbondioxide::Sun(std::vector<std::unique_ptr<MyElement>> &e)
{
    e.push_back(std::move(std::make_unique<Oxigen>(thickness*0.05)));
    //e.push_back(std::move(std::make_unique<Carbondioxide>(thickness*0.95)));
    thickness = thickness*0.95;
}



void Ozone::Other(std::vector<std::unique_ptr<MyElement>> &e) 
{
    e.push_back(std::move(std::make_unique<Oxigen>(thickness*0.05)));
    //e.push_back(std::move(std::make_unique<Ozone>(thickness*0.95)));
    thickness = thickness*0.95;
}

void Oxigen::Other(std::vector<std::unique_ptr<MyElement>> &e) 
{
    e.push_back(std::move(std::make_unique<Carbondioxide>(thickness*0.1)));
    //e.push_back(std::move(std::make_unique<Oxigen>(thickness*0.9)));
    thickness = thickness*0.9;
}

void Carbondioxide::Other(std::vector<std::unique_ptr<MyElement>> &e) 
{
    e.push_back(std::move(std::make_unique<Carbondioxide>(0)));
}
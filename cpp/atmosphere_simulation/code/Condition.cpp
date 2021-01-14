#include "Condition.hpp"
#include<iostream>


void Rain::Change(std::vector<std::unique_ptr<MyElement>> &e,std::queue<std::unique_ptr<Condition>> &c)
{
    c.push(std::move(std::make_unique<Rain>()));
    c.pop();

    std::vector<std::unique_ptr<MyElement>> split;
    split.resize(0);

    for(int i=e.size()-1; i>=0; i--){
        e[i]->Rain(split);
    }

    LayersRise(e,split);
}

void Sun::Change(std::vector<std::unique_ptr<MyElement>> &e,std::queue<std::unique_ptr<Condition>> &c)
{
    c.push(std::move(std::make_unique<Sun>()));
    c.pop();

    std::vector<std::unique_ptr<MyElement>> split;
    split.resize(0);
    
    for(int i=e.size()-1; i>=0; i--){
        e[i]->Sun(split);
    }

    LayersRise(e,split);
}

void Other::Change(std::vector<std::unique_ptr<MyElement>> &e,std::queue<std::unique_ptr<Condition>> &c)
{
    c.push(std::move(std::make_unique<Other>()));
    c.pop();

    std::vector<std::unique_ptr<MyElement>> split;
    split.resize(0);    

    for(int i=e.size()-1; i>=0; i--){
        e[i]->Other(split);
    }

    LayersRise(e,split);
}

void Condition::LayersRise(std::vector<std::unique_ptr<MyElement>> &e,std::vector<std::unique_ptr<MyElement>> &split)
{

    int startj=e.size();
    for(int i=0; i<split.size();i++){
        bool layerdone = false;
        int j=startj-(i+1);
        while(!layerdone && j>=0){
            char splitchr = split[i]->GetType();
            char echr = e[j]->GetType();
            if(splitchr == echr){
                if((split[i]->GetSize()+e[j]->GetSize())>=0.5)
                {
                    e[j]->ChangeThickness(split[i]->GetSize()+e[j]->GetSize());
                    layerdone = true;
                }else {
                    float sum = split[i]->GetSize()+e[j]->GetSize();
                    split[i]->ChangeThickness(sum);
                    e[j]->ChangeThickness(0);
                }
            } 
            j--;
        }
        if(!layerdone && split[i]->GetSize() > 0.5){
            e.insert(e.begin(),std::move(split[i]));
        }
    }

    for(int i=0; i<e.size();i++){
        if(e[i]->GetSize() < 0.5){
            e.erase(e.begin()+i);
        }
    }

    split.resize(0);
    split.shrink_to_fit();
}
#pragma once

#include "MyElement.hpp"
#include<queue>


class Condition
{
    public:
        virtual void Change(std::vector<std::unique_ptr<MyElement>> &e,std::queue<std::unique_ptr<Condition>> &c)=0;
        virtual char GetType() const=0;

    protected:
        void LayersRise(std::vector<std::unique_ptr<MyElement>> &e,std::vector<std::unique_ptr<MyElement>> &split);
};

class Rain : public Condition
{
    public:
        Rain(){};
        void Change(std::vector<std::unique_ptr<MyElement>> &e,std::queue<std::unique_ptr<Condition>> &c) override;
        char GetType() const override {return 'z';}
};


class Sun : public Condition
{
    public:
        Sun(){};
        void Change(std::vector<std::unique_ptr<MyElement>> &e,std::queue<std::unique_ptr<Condition>> &c) override;
        char GetType() const override {return 'n';}
};


class Other : public Condition
{
    public:
        Other(){};
        void Change(std::vector<std::unique_ptr<MyElement>> &e,std::queue<std::unique_ptr<Condition>> &c) override;
        char GetType() const override {return 'm';}
};


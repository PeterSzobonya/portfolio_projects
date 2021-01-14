#pragma once
#include<vector>
#include<memory>


class MyElement
{
    public:
        virtual float GetSize() const=0;
        virtual void Rain(std::vector<std::unique_ptr<MyElement>> &e)=0;
        virtual void Sun(std::vector<std::unique_ptr<MyElement>> &e)=0;
        virtual void Other(std::vector<std::unique_ptr<MyElement>> &e) =0;
        virtual char GetType() const=0;
		virtual void ChangeThickness(float t) =0;
};

class Ozone : public MyElement
{
    private:
        float thickness;
    public:
        Ozone(float t) : thickness(t){};

        float GetSize() const override{return thickness;};
        void Rain(std::vector<std::unique_ptr<MyElement>> &e) override;
        void Sun(std::vector<std::unique_ptr<MyElement>> &e) override;
        void Other(std::vector<std::unique_ptr<MyElement>> &e)  override;
        char GetType() const override {return 'z';}
        void ChangeThickness(float t) override {thickness = t;}
};


class Oxigen : public MyElement
{
    private:
        float thickness;
    public:
        Oxigen(float t) : thickness(t){}

        float GetSize() const override {return thickness;}
        void Rain(std::vector<std::unique_ptr<MyElement>> &e) override;
        void Sun(std::vector<std::unique_ptr<MyElement>> &e) override;
        void Other(std::vector<std::unique_ptr<MyElement>> &e)  override;
        char GetType() const override {return 'x';}
        void ChangeThickness(float t) override {thickness = t;}
};


class Carbondioxide : public MyElement
{
    private:
        float thickness;
    public:
        Carbondioxide(float t) : thickness(t){}

        float GetSize() const override {return thickness;}
        void Rain(std::vector<std::unique_ptr<MyElement>> &e) override;
        void Sun(std::vector<std::unique_ptr<MyElement>> &e)override;
        void Other(std::vector<std::unique_ptr<MyElement>> &e)  override;
        char GetType() const override {return 's';}
        void ChangeThickness(float t) override {thickness = t;}
};

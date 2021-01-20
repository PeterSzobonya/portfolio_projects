#include "Simulate.hpp"


Simulate::Simulate(const std::string &filename)
{
    std::ifstream f(filename);
    f>>startLayers;
    if(startLayers<3){
        throw TooFewArguments;
    } else{
        char tempchar;
        float tempfloat;
        for(int i=0; i<startLayers;i++)
        {
            f >> tempchar;
            f >> tempfloat;
            switch(tempchar)
            {
                case 'z': layers.push_back(std::move(std::make_unique<Ozone>(tempfloat)));
                    break;
                case 'x' : layers.push_back(std::move(std::make_unique<Oxigen>(tempfloat)));
                    break;
                case 's' : layers.push_back(std::move(std::make_unique<Carbondioxide>(tempfloat)));
            }
        }
        std::string tempstring;
        f >> tempstring;
        for(int i=0; i<tempstring.length();i++)
        {
            switch(tempstring.at(i))
            {
                case 'm' : conditions.push(std::move(std::make_unique<Other>()));
                    break;
                case 'n' : conditions.push(std::move(std::make_unique<Sun>()));
                    break;
                case 'z' : conditions.push(std::move(std::make_unique<Rain>()));
            }
        }
    }
}


void Simulate::PrintCurrent()
{
    for(int i=0; i<layers.size(); ++i){
        std::cout << layers[i] ->GetType() << " " << layers[i] -> GetSize() << std::endl;
    }
    std::cout << "\n";
}

void Simulate::Next()
{
    conditions.front()->Change(layers,conditions);
    end = layers.size() > startLayers*3 || layers.size()<3;
}


bool Simulate::End(){
    return end;
}

void Simulate::WriteCurrent()
{
    std::ofstream write("result.txt");
    for(int i=0; i<layers.size(); ++i){
        write << layers[i]->GetType() << " " << layers[i]->GetSize() << std::endl;
    }
}
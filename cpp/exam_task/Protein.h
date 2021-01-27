//
// add class definition etc. here
//
// ID --> stored as string
// sequence --> uppercase characters stored as string
// label --> '-', '#' signs, same length as sequence
// '-' --> amino acid is part of folded region, '#' --> amino acid is part of disordered region
// foldIndex --> value of foldIndex for that protein or 0 if no value is computed yet, stored as double

// default and ordinary constructors
// accessor methods

// method called setFoldIndex --> set value for foldIndex
// full set of relational operators that order Protein objects based on foldIndex value
// operators << and >> for stream based I/O (format must be identical, std::quoted)
// interfaces must be compatible for main.cpp
//


#ifndef PROTEIN_H
#define PROTEIN_H


#include <string>
#include <iostream>
#include <sstream>

class Protein{

private:
    std::string ID;
    std::string sequence;
    std::string label;
    double foldIndex;

public:
    //default constructor
    Protein();
    //ordinary constructor
    Protein(std::string ID, std::string sequence, std::string label, double foldIndex);

    //accessor methods
    std::string getID() const{
        return this->ID;
    }
    std::string getSequence() const{
        return this->sequence;
    }
    std::string getLabel() const{
        return this->label;
    }
     double getFoldIndex() const{
        return this->foldIndex;
    }

    //mutator methods
    void setID(std::string ID){
        this->ID = ID;
    }
    void setSequence(std::string sequence){
        this->sequence = sequence;
    }
    void setLabel(std::string label){
        this->label = label;
    }
    void setFoldIndex(double fold){
        foldIndex = fold;
        
    }

    friend std::ostream& operator<<(std::ostream&os,const Protein& prot);

};

//operator overloading for I/O
std::ostream& operator<<(std::ostream &os,const Protein& protein);
std::istream& operator>>(std::istream& is, Protein &protein);

//operator overloading for the full set of relational operators



inline bool operator>(const Protein &a, const Protein &b){
    return a.getFoldIndex() > b.getFoldIndex();
}


#endif // PROTEIN_H
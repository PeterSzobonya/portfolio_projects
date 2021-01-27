//
// add definitions of non-inline methods & functions etc. here
//

#include "Protein.h"
#include <iostream>
#include <string>
#include <iomanip>
#include <utility>

Protein::Protein() {
    ID = "0";
    sequence = "0";
    label = "0";
    foldIndex = 0;
}
Protein::Protein(std::string ID, std::string sequence, std::string label, double foldIndex) {
    this->ID = std::move(ID);
    this->sequence = std::move(sequence);
    this->label = label;
    this->foldIndex = foldIndex;
}

//operator overloading

std::istream& operator>>(std::istream& is, Protein& protein){
    std::string ID, sequence, label;
    double foldIndex;

    char separator;

    if(is>>std::quoted(ID)>>separator>>std::quoted(sequence)>>separator>>std::quoted(label)>>separator>>foldIndex){
        if(separator == ','){
            protein = Protein(ID, sequence, label, foldIndex);
        }
        else{
            is.clear(std::ios_base::failbit);
        }
    }
    return is;
}

std::ostream& operator<<(std::ostream&os,const Protein& prot){
    os << prot.ID << "," << prot.sequence << "," << prot.label << "," << prot.foldIndex;
    return os;
}
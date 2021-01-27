/******************************************************************************

File        : main.cpp

Date        : Tuesday 12th January 2021

Author      : Gavin Cawley

Description : Prototype implementation of a program to identify proteins with
              the greatest propensity to intrinsic disorder, according to the
              FoldIndex method [1].

              Note that this program is just a prototype system to be used to
              refine the project specifications.  There may be bugs and areas
              where the code is inefficient.  These issues will be addressed
              as the program is iteratively improved/refactored.

References  : [1] Prilusky, et al., "FoldIndex: a simple tool to predict
                  whether a given protein sequence is intrinsically
                  unfolded", Bioinformatics, vol. 21, no. 16, pp. 3435-3438,
                  2005. doi: 10.1093/bioinformatics/bti537

              [2]  Kyte, J and Doolittle, R. F., "A simple method for
                   displaying the hydropathic character of a protein",
                   Journal of Molecular Biology, vol. 157, no. 1, pp. 105–32,
                   1982. doi: 10.1016/0022-2836(82)90515-0

History     : 12/01/2021 - v1.00 Initial prototype
              13/01/2021 - v1.10 Added displayProtein and saving of ordered
                                 protein database with calculated FoldIndex
                                 values.

******************************************************************************/

#include <iostream>
#include <iomanip>
#include <fstream>
#include <cstdlib>
#include <vector>
#include <limits>
#include <cmath>

#include "Protein.h"

using namespace std;

// declarations of functions defined later in this file

double foldIndex(const string &sequence);

template <typename T, typename F> void bubbleSort(vector<T> &vec, F compare);

void displayProtein(const Protein &protein);

int main()
{
   // load the database of disordered proteins from the file system

   vector<Protein> proteins;

   Protein prot;

   // load disprot database of intrinsically disordered proteins

   ifstream is("disprot.csv", ifstream::in);

   while (is >> prot)
   {
      proteins.push_back(prot);
   }

   is.close();

   // compute the FoldIndex for each protein

   for (int i=0; i<proteins.size(); i++)
   {
      proteins[i].setFoldIndex(foldIndex(proteins[i].getSequence()));
   }

   // sort proteins in order of their propensity for disorder

   bubbleSort(proteins, [](const Protein &a, const Protein &b) {return a > b;});

   // display the protein with the highest propensity for intrinsic disorder


   displayProtein(proteins[0]);

   

   // save list of proteins

   ofstream os("foldindex.csv");

   for (const Protein &protein : proteins)
   {
      os << protein << endl;
   }

   os.close();

   return EXIT_SUCCESS;
}

/******************************************************************************

Function    : aminoAcidCharge

Parameters  : symbol - a character representing an amino acid (implementing
                       some, but not all wild-card symbols).

Returns     : double - charge

Description : Returns the electrostatic charge for the specified amino acid.
              An error message is generated if the amino acid is not
              recognised, and the program terminates.

******************************************************************************/

double aminoAcidCharge(char symbol)
{
   static double value[] = {
      +0,    // Alanine (A)
      -0.5,  // (B) is Asparagine (N = +0) or Aspartic acid (D = -1), so use average
      +0,    // Cysteine (C)
      -1,    // Aspartic acid (D)
      -1,    // Glutamic acid (E)
      +0,    // Phenylalanine (F)
      +0,    // Glycine (G)
      +1,    // Histidine (H)
      +0,    // Isoleucine (I)
      numeric_limits<double>::quiet_NaN(),
      +1,    // Lysine (K)
      +0,    // Leucine (L)
      +0,    // Methionine (M)
      +0,    // Asparagine (N)
      numeric_limits<double>::quiet_NaN(),
      +0,    // Proline (P)
      +0,    // Glutamine (Q)
      +1,    // Arginine (R)
      +0,    // Serine (S)
      +0,    // Threonine (T)
      numeric_limits<double>::quiet_NaN(),
      +0,    // Valine (V)
      +0,    // Tryptophan (W)
      numeric_limits<double>::quiet_NaN(),
      +0,    // Tyrosine (Y)
      -0.5   // Z is Glutamic acid (E = -1) or Glutamine (Q = +0), so use average
   };

   if (symbol < 'A' || symbol > 'Z' || isnan(value[symbol - 'A']))
   {
      cerr << "Error: Unrecognised amino acid '" << symbol << "'" << endl;

      exit(EXIT_FAILURE);
   }

   return value[symbol - 'A'];
}

/******************************************************************************

Function    : aminoAcidHydrophobicity

Parameters  : symbol - a character representing an amino acid (implementing
                       some, but not all wild-card symbols).

Returns     : double - hydrophobicity

Description : Returns the hydrophobicity (at a pH of 7) for the specified
              amino acid, according to the Kyte-Doolittle scale [2],
              normalised to lie in the range 0 - 1.  An error message is
              generated if the amino acid is not recognised, and the
              program terminates.

******************************************************************************/

double aminoAcidHydrophobicity(char symbol)
{
   static double value[] = {
      0.7,     // Alanine (A = +1.8 )
      0.1111,  // B is Asparagine (N = -3.5) or Aspartic acid (D = -3.5)
      0.7778,  // Cysteine (C = +2.5)
      0.1111,  // Aspartic acid (D = -3.5)
      0.1111,  // Glutamic acid (E = -3.5)
      0.8111,  // Phenylalanine (F = +2.8)
      0.4556,  // Glycine (G = -0.4)
      0.1444,  // Histidine (H = -3.2)
      1.0000,  // Isoleucine (I = +4.5)
      numeric_limits<double>::quiet_NaN(),
      0.0667,  // Lysine (K = -3.9)
      0.9222,  // Leucine (L = +3.8)
      0.7111,  // Methionine (M = +1.9)
      0.1111,  // Asparagine (N = -3.5)
      numeric_limits<double>::quiet_NaN(),
      0.3222,  // Proline (P = -1.6)
      0.1111,  // Glutamine (Q = -3.5)
      0.0000,  // Arginine (R = -4.5)
      0.4111,   // Serine (S = -0.8)
      0.4222,  // Threonine (T = -0.7)
      numeric_limits<double>::quiet_NaN(),
      0.9667,  // Valine (V = +4.2)
      0.4000,  // Tryptophan (W = -0.9)
      numeric_limits<double>::quiet_NaN(),
      0.3556,  // Tyrosine (Y = -1.3)
      0.1111   // Z is Glutamic acid (E = -3.5) or Glutamine (Q = -3.5), so use average
   };

   if (symbol < 'A' || symbol > 'Z' || isnan(value[symbol - 'A']))
   {
      cerr << "Error: Unrecognised amino acid '" << symbol << "'" << endl;

      exit(EXIT_FAILURE);
   }

   return value[symbol - 'A'];
}

/******************************************************************************

Function    : foldIndex

Parameters  : sequence - a const reference to a string containing the amino
                         acid sequence of a protein.

Returns     : double - value of foldIndex

Description : Evaluate FoldIndex [1], a numeric inidcation of the propensity
              for intrinsic disorder, of a protein, based only on the amino
              acid sequence.  The string must only contain valid symbols
              corresponding to amino acids, and the behaviour of the function
              is undefined if this is not the case.

******************************************************************************/

double foldIndex(const string &sequence)
{
   double charge = 0.0;

   double hydrophobicity = 0.0;

   for (char symbol : sequence)
   {
      charge         += aminoAcidCharge(symbol);
      hydrophobicity += aminoAcidHydrophobicity(symbol);
   }

   hydrophobicity = hydrophobicity/sequence.size();
   charge         = charge/sequence.size();

   return 2.785*hydrophobicity - abs(charge) - 1.151;
}

/******************************************************************************

Function    : bubbleSort (template)

Parameters  : T       - template parameter given base type of vector
              F       - template parameter giving lambda/function pointer type
              vec     - reference to vector of base type T
              compare - lambda or function pointer used to define ordering

Returns     : void

Description : Basic implementation of bubble sort, implemented as a template
              so that it can be used with vectors of different types.  Bubble
              sort is not very efficient for large vectors, but is fine for
              prototyping with small datasets.  The compare function returns
              a true value if a pair of adjacent elements should be swapped
              and false otherwise.

******************************************************************************/

template <typename T, typename F> void bubbleSort(vector<T> &vec, F compare)
{
   int i, j;
   Protein temp;

    for(i = 0; i < vec.size(); i++)
    {
        for(j = 0; j < vec.size()-i-1; j++)
        {
            if( compare(vec[j], vec[j+1]))
            {
                // swap the elements
                temp = vec[j];
                vec[j] = vec[j+1];
                vec[j+1] = temp;
            } 
        }
    }
}

/******************************************************************************

Function    : displayProtein

Parameters  : protein - const reference to a Protein object

Returns     : void

Description : Display the amino-acids comprising a protein, along with a
              label indicating whether it belongs to an folded ('-') or
              an instrinsically dispordered ('#') segment, and the
              contribution of that amino acid to the value of FoldIndex.
              The ID of the protein and the value of FoldIndex for the
              whole sequence are also displayed.

******************************************************************************/

void displayProtein(const Protein &protein)
{
   cout << "Protein ID = " << protein.getID() << endl;
   cout << "FoldIndex  = " << protein.getFoldIndex() << endl;

   const string &sequence = protein.getSequence();
   const string &label    = protein.getLabel();

   if (sequence.length() != label.length())
   {
      cerr << "Error: sequence and label strings have different lengths"
           << endl;

      exit(EXIT_FAILURE);
   }

   for (int i = 0; i < sequence.length(); i++)
   {
      double contribution = 2.785*aminoAcidHydrophobicity(sequence[i])
                          - abs(aminoAcidCharge(sequence[i]));

      cout << setw(4) << noshowpos << i
           << "   " << sequence[i]
           << "   " << label[i]
           << "   " << showpos << setprecision(5)
           << contribution/sequence.length()
           << endl;
   }
}

/*****************************************************************************/

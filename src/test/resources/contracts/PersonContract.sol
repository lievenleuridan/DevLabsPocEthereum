pragma solidity ^0.4.6;


contract PersonContract {

    mapping  (uint=>Person) internal persons;
    address internal owner;

    struct Person {
    string firstName;
    string lastName;
    }

    function PersonContract() public {
        owner = msg.sender;
    }

    //cannot take in Person struct as parameter because external caller will have no knowledge of internal object
    //maybe builder contracts can be solution at scale?
    function registerPerson(string firstName, string lastName, uint id)  public {
        persons[id] = Person(firstName, lastName);
    }

    //can't return a struct either but can return multiple things so when return a struct
    //we have to just return every property of that struct
    //I suppose solidity isn't a very mature languague yet...
    function findPerson(uint personId) public view returns (string, string){
        return (persons[personId].firstName, persons[personId].lastName);
    }
}
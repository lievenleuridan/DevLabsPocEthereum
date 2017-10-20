pragma solidity ^0.4.6;

contract RealDolmenCoinContract {

    mapping (address => uint) public balances;
    address public owner;

    function RealDolmenCoinContract() public{
        balances[msg.sender] = 1000000;
        owner = msg.sender;
    }

    function send(address receiver, uint amount) public{
        if (balances[msg.sender] < amount) revert();
        balances[msg.sender] -= amount;
        balances[receiver] += amount;
    }

    function getAddress() public view returns (address) {
        return owner;
    }

    function getBalance(address balanceAddress) public view returns (uint){
        return balances[balanceAddress];
    }

}

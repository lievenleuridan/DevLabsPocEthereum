Devlabs
 
Team 1: Voting system

	- users can register for the vote
	- Admin can add voting subjects
	- (Option) admin can approve newly registered users
	- Closed voting, admin can request the total tally
	- or voting gets closed after set timeframe and admin gets notified
Team 2: Cryptocoin

	- users can make accounts
	- users can send/receive money
	- users can consult their account deposit
	- watch out for double spends, locks, no negative saldo,...
Team 3: Document ledger

	- Pdf/txt gets changed to java string and hashed
	- The hash gets safed to the chain
	- 2 users can approve the safed hash along with metadata about who approved it
Team 4: infrastucture

	- Safe a contract to ropsten test network from java
	- Safe a contract to local private network from java
	- Optionally safe a contract to the mainnet (costs a little bit of money)
	
(Optional): Interplanety file system

	- Safe a document to an IPFS
	- Infura has support for this, should check out pricing, complexity, etc...
	
(Optional): Security deposit system

	- Money is held by the contract until certain conditions are met
	- 2 out 3 system where 2 actors and 1 mediator have a vote and 2 out of 3 need to agree to release funds
	- "Oracles" where 2 people agree to a transaction based on the given date from an oracle contract 
		- Ex: 2 people agree to a buy of stocks at say 100$, A gets a return of 10% fixed no matter the profit/loss based on a third party contract, B gets the rest. 

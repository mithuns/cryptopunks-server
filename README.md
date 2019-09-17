#CryptoPunks Sale Tracker

## Steps to run

1. Created infura account and got the url for free cloud client.
1. Created wallet using web3j-cli
1. Generated java contract wrapper code using abi json from etherscan and using web3j-cli solidity command.
1. Download json data for static info like cryptopunk attributes.
1. Uploaded json data directly into h2 db on server startup
1. Query blockchain for all 10k cryptopunks and check if they are on sale and thus update local database on server startup.
1. Set up event filter to capture events (PunkNoLongerForSale, PunkOffered) and follow simple strategy:-
  if punk exists in db since it was on sale - and we got PunkNoLongerForSale, we remove that punk
  if punk didnt exist in db and we got PunkOffered , we add that punk to our db.
1. Infura seems to not support attaching event listeners. Seems like a api limitation(as per a number of blogs and stackoverflow posts)


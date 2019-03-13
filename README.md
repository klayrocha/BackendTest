After the git clone, run the  <strong>mvn clean compile</strong> command.

Then run <strong>mvn install</strong> command, you can see the tests being run.

Then run <strong>mvn exec:java</strong> command.

After executing the commands, import the Revolut.postman_collection.json file from the doc folder in Postman to test the services.

Example:

# Transfer money
POST: http://localhost:8085/revolut/transaction

{
    "ibanDebit": "PT233920012223230888",
    "ibanCredit": "PT111999992223230790",
    "value": "0",
    "description": "Test"
}

# Insert account
POST: http://localhost:8085/revolut/account/

{
	"number": 3920012223230777,
    "ownerAccount": "João Pedro Rocha",
    "iban": "PT233920012223230777",
    "bic": "BIMOMZMX",
    "balance": 500,
    "transactions": []
}

# Find Account
GET: http://localhost:8085/revolut/account/PT233920012223230888

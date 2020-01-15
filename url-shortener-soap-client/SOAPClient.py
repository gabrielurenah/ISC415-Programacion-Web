from suds.client import Client

# Javier Rojas 2016-0197
# Gabriel Urena 2016-0217

SOAP_URL = 'http://localhost:7777/ws/UrlWebServiceImpl'
WSDL_URL = 'http://localhost:7777/ws/UrlWebServiceImpl?wsdl'

client = Client(url=WSDL_URL, location=SOAP_URL)

print('Welcome to the SOAP client!')
while True:
    option = int(input(
        '''
Select the option you want to execute
1- List of URLs from Username
2- shorten one URL
4- Exit
'''))
    if (option == 1):
        all_urls = client.service.getUrlsFromUser('Admin')
        print('Viendo los urls creados por: Admin')
        print(all_urls)
    elif (option == 2):
        new_url = client.service.shortenUrl(
            'https://github.com/gabrielurenah/spark-url-shortener', 'Admin')
        print(new_url)
    elif (option == 4):
        print('bye')
        break
    int(input("\nPress any number then enter to continue"))

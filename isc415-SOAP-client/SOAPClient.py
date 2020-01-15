from suds.client import Client

# Javier Rojas 2016-0197
# Gabriel Urena 2016-0217

SOAP_URL = 'http://localhost:7777/ws/AcademicoWebService'
WSDL_URL = 'http://localhost:7777/ws/AcademicoWebService?wsdl'

client = Client(url=WSDL_URL, location=SOAP_URL)

print('Welcome to the SOAP client!🤖')
while True:
    option = int(input(
'''
Select the option you want to execute
1- List of students📝
2- List of a class📝
3- Details of a teacher📝
4- Exit
'''))
    if (option == 1):
        all_students = client.service.getAllEstudiante()
        for student in all_students:
            print('ID: ' + student['matricula'])
            print('Name: ' + student['nombre'])
    elif (option == 2):
        id = input('\nType the class id: ')
        print(client.service.getAsignatura(id))
    elif (option == 3):
        id = input("\Type the teacher id: ")
        print(client.service.getProfesor(id))
    elif (option == 4):
        print('bye 👋')
        break
    input("\nPress enter to continue")

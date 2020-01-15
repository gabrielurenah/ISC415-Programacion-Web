const axios = require('axios');
const readline = require('readline').createInterface({
  input: process.stdin,
  output: process.stdout
})

console.log('Digite la opcion: \n1.Listar Estudiantes\n2.Buscar Estudiante\n3.Crear Estudiante\n4. Salir\n');
const recursiveAsyncReadLine = () => {
  readline.question(``, (opt) => {
    if (opt == 1) {
      axios.get('http://localhost:4567/rest/estudiantes/')
        .then(res => console.table(res.data))
        .catch(err => console.log(err));
    } else if (opt == 2) {
      readline.question(`Matricula del estudiante a buscar: `, id => {
        axios.get(`http://localhost:4567/rest/estudiantes/${id}`)
          .then(response => console.table(response.data))
          .catch(err => console.log(err));
        recursiveAsyncReadLine();
      });
    } else if (opt == 3) {
      readline.question(`Introduzca nombre, correo y carrera (Separados por comas y sin espacios)\nej: Julian Perez,miemail@email.com,MiCarrera:\n`, (str) => {
        const student = str.split(',');
        axios.post('http://localhost:4567/rest/estudiantes/', { nombre: student[0], correo: student[1], carrera: student[2] })
          .then(res => {
            console.log("Completed.")
            console.table(res.data)
          })
          .catch(err => console.log(err));
        recursiveAsyncReadLine();
      })
    } else console.log('not an option')
    recursiveAsyncReadLine();
  })
};

recursiveAsyncReadLine();

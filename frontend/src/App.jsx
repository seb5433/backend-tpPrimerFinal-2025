import React from "react";
import RegistrarServicio from "./components/RegistrarServicio.jsx";
import RegistrarMecanico from "./components/RegistrarMecanico.jsx";
import Registrarrepuesto from "./components/RegistrarRepuesto.jsx";
import RegistrarCliente from "./components/RegistrarCliente.jsx";
import RegistrarVehiculo from "./components/RegistrarVehiculo.jsx";
import ConsultarServicios from "./components/ConsultarServicios.jsx";

function App() {
  return (
    <div className="min-vh-100 d-flex justify-content-center bg-dark py-5">
      <div className="bg-secondary text-light rounded p-5 shadow-lg w-100" style={{maxWidth: 900}}>
        <h1 className="mb-4 text-center">Bienvenido</h1>
        <p className="text-center">Sistema de gestión de servicios automotrices</p>
        <RegistrarCliente />
        <RegistrarServicio />
        <RegistrarVehiculo />
        <RegistrarMecanico />
        <Registrarrepuesto />
        <ConsultarServicios />
      </div>
    </div>
  );
}

export default App;
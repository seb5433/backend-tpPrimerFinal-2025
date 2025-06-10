import React, { useState, useEffect } from "react";

export default function RegistrarVehiculo() {
  const [form, setForm] = useState({
    marca: "",
    numeroChapa: "",
    modelo: "",
    anio: "",
    tipo: "",
    idCliente: "",
  });

  const [clientes, setClientes] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/examenfinal/clientes")
      .then((res) => res.json())
      .then((data) => setClientes(data))
      .catch(() => setClientes([]));
  }, []);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/examenfinal/vehiculos", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });
      if (response.ok) {
        alert("Vehículo registrado exitosamente");
        setForm({
          marca: "",
          numeroChapa: "",
          modelo: "",
          anio: "",
          tipo: "",
          idCliente: "",
        });
      } else {
        const error = await response.text();
        alert("Error al registrar vehículo: " + error);
      }
    } catch (err) {
      alert("Error de conexión: " + err.message);
    }
  };

  return (
    <div className="container mt-4 bg-dark text-light rounded p-4">
      <h2 className="mb-4">Registrar Vehículo</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="marca"
            placeholder="Marca"
            value={form.marca}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="numeroChapa"
            placeholder="Número de chapa"
            value={form.numeroChapa}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="modelo"
            placeholder="Modelo"
            value={form.modelo}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="anio"
            type="number"
            placeholder="Año"
            value={form.anio}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <select
            className="form-select bg-secondary text-light border-0"
            name="tipo"
            value={form.tipo}
            onChange={handleChange}
            required
          >
            <option value="">Tipo</option>
            <option value="MOTO">Moto</option>
            <option value="COCHE">Coche</option>
            <option value="CAMIONETA">Camioneta</option>
            <option value="CAMION">Camión</option>
          </select>
        </div>
        <div className="mb-4">
          <select
            className="form-select bg-secondary text-light border-0"
            name="idCliente"
            value={form.idCliente}
            onChange={handleChange}
            required
          >
            <option value="">Cliente propietario</option>
            {clientes.map((c) =>
              c.idCliente ? (
                <option key={c.idCliente} value={c.idCliente}>
                  {c.nombre} {c.apellido}
                </option>
              ) : null
            )}
          </select>
        </div>
        <button type="submit" className="btn btn-primary w-100">
          Registrar
        </button>
      </form>
    </div>
  );
}
import React, { useState } from "react";

export default function RegistrarCliente() {
  const [form, setForm] = useState({
    nombre: "",
    apellido: "",
    cedula: "",
    email: "",
    direccion: "",
    tipoCliente: "",
  });

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/examenfinal/clientes", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });
      if (response.ok) {
        alert("Cliente registrado exitosamente");
        setForm({
          nombre: "",
          apellido: "",
          cedula: "",
          email: "",
          direccion: "",
          tipoCliente: "",
        });
      } else {
        const error = await response.text();
        alert("Error al registrar cliente: " + error);
      }
    } catch (err) {
      alert("Error de conexión: " + err.message);
    }
  };

  return (
    <div className="container mt-4 bg-dark text-light rounded p-4">
      <h2 className="mb-4">Registrar Cliente</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="nombre"
            placeholder="Nombre"
            value={form.nombre}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="apellido"
            placeholder="Apellido"
            value={form.apellido}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="cedula"
            placeholder="Cédula"
            value={form.cedula}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="email"
            placeholder="Email"
            value={form.email}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="direccion"
            placeholder="Dirección"
            value={form.direccion}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-4">
          <select
            className="form-select bg-secondary text-light border-0"
            name="tipoCliente"
            value={form.tipoCliente}
            onChange={handleChange}
            required
          >
            <option value="">Tipo de cliente</option>
            <option value="OCASIONAL">Ocasional</option>
            <option value="REGULAR">Regular</option>
            <option value="VIP">VIP</option>
          </select>
        </div>
        <button type="submit" className="btn btn-primary w-100">
          Registrar
        </button>
      </form>
    </div>
  );
}
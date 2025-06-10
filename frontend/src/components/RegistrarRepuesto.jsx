import React, { useState } from "react";

export default function RegistrarRepuesto() {
  const [form, setForm] = useState({
    nombre: "",
    codigo: "",
  });

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/examenfinal/repuestos", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });
      if (response.ok) {
        alert("Repuesto registrado exitosamente");
        setForm({ nombre: "", codigo: "" });
      } else {
        const error = await response.text();
        alert("Error al registrar repuesto: " + error);
      }
    } catch (err) {
      alert("Error de conexión: " + err.message);
    }
  };

  return (
    <div className="container mt-4 bg-dark text-light rounded p-4">
      <h2 className="mb-4">Registrar Repuesto</h2>
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
        <div className="mb-4">
          <input
            className="form-control bg-secondary text-light border-0"
            name="codigo"
            placeholder="Código"
            value={form.codigo}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit" className="btn btn-primary w-100">
          Registrar
        </button>
      </form>
    </div>
  );
}
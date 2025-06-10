import React, { useState, useEffect } from "react";

export default function RegistrarServicio() {
  const [form, setForm] = useState({
    idVehiculo: "",
    fecha: "",
    descripcionGeneral: "",
    kilometrajeActual: "",
    costoTotal: "",
    detalles: [],
  });

  const [detalle, setDetalle] = useState({
    descripcion: "",
    costo: "",
    idRepuestos: [],
    idMecanicos: [],
  });

  const [repuestos, setRepuestos] = useState([]);
  const [mecanicos, setMecanicos] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/examenfinal/repuestos")
      .then((res) => res.json())
      .then((data) => setRepuestos(data))
      .catch(() => setRepuestos([]));

    fetch("http://localhost:8080/examenfinal/mecanicos")
      .then((res) => res.json())
      .then((data) => setMecanicos(data))
      .catch(() => setMecanicos([]));
  }, []);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleDetalleChange = (e) => {
    const { name, value, options } = e.target;
    if (options) {
      const values = Array.from(options).filter(o => o.selected).map(o => parseInt(o.value));
      setDetalle({ ...detalle, [name]: values });
    } else {
      setDetalle({ ...detalle, [name]: value });
    }
  };

  const agregarDetalle = (e) => {
    e.preventDefault();
    if (!detalle.descripcion || !detalle.costo) {
      alert("Completa la descripción y el costo del detalle.");
      return;
    }
    setForm({ ...form, detalles: [...form.detalles, detalle] });
    setDetalle({ descripcion: "", costo: "", idRepuestos: [], idMecanicos: [] });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/examenfinal/servicios", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });
      if (response.ok) {
        alert("Servicio registrado!");
        setForm({
          idVehiculo: "",
          fecha: "",
          descripcionGeneral: "",
          kilometrajeActual: "",
          costoTotal: "",
          detalles: [],
        });
      } else {
        const error = await response.text();
        alert("Error al registrar servicio: " + error);
      }
    } catch (err) {
      alert("Error de conexión: " + err.message);
    }
  };

  return (
    <div className="container mt-4 bg-dark text-light rounded p-4">
      <h2 className="mb-4">Registrar Servicio</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="idVehiculo"
            placeholder="ID Vehículo"
            value={form.idVehiculo}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="fecha"
            type="date"
            value={form.fecha}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="descripcionGeneral"
            placeholder="Descripción general"
            value={form.descripcionGeneral}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="kilometrajeActual"
            type="number"
            placeholder="Kilometraje actual"
            value={form.kilometrajeActual}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-4">
          <input
            className="form-control bg-secondary text-light border-0"
            name="costoTotal"
            type="number"
            placeholder="Costo total"
            value={form.costoTotal}
            onChange={handleChange}
            required
          />
        </div>

        <h4 className="mb-3">Detalles del Servicio</h4>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="descripcion"
            placeholder="Descripción del trabajo"
            value={detalle.descripcion}
            onChange={handleDetalleChange}
          />
        </div>
        <div className="mb-3">
          <input
            className="form-control bg-secondary text-light border-0"
            name="costo"
            type="number"
            placeholder="Costo del detalle"
            value={detalle.costo}
            onChange={handleDetalleChange}
          />
        </div>
        <div className="mb-3">
          <select
            className="form-select bg-secondary text-light border-0"
            name="idRepuestos"
            multiple
            value={detalle.idRepuestos}
            onChange={handleDetalleChange}
          >
            {repuestos.map((r) => (
              <option key={r.idRepuesto || r.id} value={r.idRepuesto || r.id}>
                {r.nombre}
              </option>
            ))}
          </select>
        </div>
        <div className="mb-3">
          <select
            className="form-select bg-secondary text-light border-0"
            name="idMecanicos"
            multiple
            value={detalle.idMecanicos}
            onChange={handleDetalleChange}
          >
            {mecanicos.map((m) => (
              <option key={m.idMecanico || m.id} value={m.idMecanico || m.id}>
                {m.nombre}
              </option>
            ))}
          </select>
        </div>
        <button className="btn btn-outline-info w-100 mb-3" onClick={agregarDetalle}>
          Agregar detalle
        </button>
        <ul className="list-group mb-3">
          {form.detalles.map((d, i) => (
            <li key={i} className="list-group-item bg-dark text-light border-0">
              {d.descripcion} | Costo: {d.costo} | Repuestos: {d.idRepuestos.join(", ")} | Mecánicos: {d.idMecanicos.join(", ")}
            </li>
          ))}
        </ul>
        <button type="submit" className="btn btn-primary w-100">
          Registrar
        </button>
      </form>
    </div>
  );
}
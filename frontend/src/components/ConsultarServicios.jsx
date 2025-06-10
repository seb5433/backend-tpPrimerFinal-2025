import React, { useState } from "react";

export default function ConsultarServicios() {
  const [filtros, setFiltros] = useState({ cliente: "", fecha: "" });
  const [servicios, setServicios] = useState([]);
  const [selectedServicio, setSelectedServicio] = useState(null);

  const handleChange = (e) => {
    setFiltros({ ...filtros, [e.target.name]: e.target.value });
  };

  const buscarServicios = async () => {
    setSelectedServicio(null);
    try {
      let url = `http://localhost:8080/examenfinal/servicios?cliente=${encodeURIComponent(
        filtros.cliente
      )}`;
      if (filtros.fecha) {
        url += `&fecha=${encodeURIComponent(filtros.fecha)}`;
      }
      const res = await fetch(url);
      const text = await res.text();
      try {
        setServicios(JSON.parse(text));
      } catch (jsonErr) {
        console.error("Respuesta no es JSON:", text);
        setServicios([]);
      }
    } catch (e) {
      console.error("Error fetching servicios:", e);
      setServicios([]);
    }
  };

  return (
    <div className="container mt-4 bg-dark text-light rounded p-4">
      <h2 className="mb-4">Consultar Servicios</h2>
      <div className="row g-2 mb-3">
        <div className="col-md-5">
          <input
            className="form-control bg-secondary text-light border-0"
            name="cliente"
            placeholder="Cliente"
            onChange={handleChange}
            value={filtros.cliente}
          />
        </div>
        <div className="col-md-4">
          <input
            className="form-control bg-secondary text-light border-0"
            name="fecha"
            type="date"
            onChange={handleChange}
            value={filtros.fecha}
          />
        </div>
        <div className="col-md-3">
          <button className="btn btn-primary w-100" onClick={buscarServicios}>
            Buscar
          </button>
        </div>
      </div>
      <ul className="list-group">
        {servicios.map((s, idx) => (
          <li
            key={idx}
            className="list-group-item d-flex justify-content-between align-items-center bg-secondary text-light border-0 mb-2"
          >
            <span>
              <strong>{s.descripcionGeneral}</strong> -{" "}
              {s.fecha ? new Date(s.fecha).toLocaleDateString() : ""}
            </span>
            <button
              className="btn btn-outline-info btn-sm"
              onClick={() => setSelectedServicio(s)}
            >
              Ver detalles
            </button>
          </li>
        ))}
      </ul>
      {selectedServicio && (
        <div className="card mt-4 bg-secondary text-light border-0">
          <div className="card-body">
            <h3 className="card-title">Detalles del Servicio</h3>
            <ul className="list-group">
              {selectedServicio.detalles && selectedServicio.detalles.length > 0 ? (
                selectedServicio.detalles.map((d, i) => (
                  <li key={i} className="list-group-item bg-dark text-light border-0">
                    <strong>{d.descripcion}</strong> - Costo: {d.costo} <br />
                    Repuestos:{" "}
                    {d.repuestos && d.repuestos.length > 0
                      ? d.repuestos.map((r, idx) => r.nombre || r.descripcion || `Repuesto #${r.id}`).join(", ")
                      : "Ninguno"}
                    <br />
                    Mecánicos:{" "}
                    {d.mecanicos && d.mecanicos.length > 0
                      ? d.mecanicos.map((m, idx) => m.nombre).join(", ")
                      : "Ninguno"}
                  </li>
                ))
              ) : (
                <li className="list-group-item bg-dark text-light border-0">
                  No hay detalles para este servicio.
                </li>
              )}
            </ul>
          </div>
        </div>
      )}
    </div>
  );
}
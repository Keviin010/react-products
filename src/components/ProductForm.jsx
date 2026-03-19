import PropTypes from "prop-types";
import { useEffect, useState } from "react";

const initialForm = {
    id: 0,
    name: '',
    description: '',
    price: ''
};

export const ProductForm = ({ handlerAdd, productSelected }) => {

    const [form, setForm] = useState(initialForm);
    const [errors, setErrors] = useState({});

    const { id, name, description, price } = form;

    // Cuando selecciono un producto desde la tabla, cargo sus datos en el form
    useEffect(() => {
        setForm(productSelected);
        setErrors({});
    }, [productSelected]);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
        setErrors({ ...errors, [e.target.name]: '' });
    };

    // Valido cada campo antes de enviar
    const validate = () => {
        const e = {};
        if (!name.trim()) e.name = 'El nombre es requerido';
        if (!description.trim()) e.description = 'La descripción es requerida';
        if (!price || isNaN(price) || Number(price) <= 0) e.price = 'Ingresa un precio válido';
        return e;
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const validationErrors = validate();
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }
        handlerAdd({ ...form, price: Number(price) });
        setForm(initialForm);
        setErrors({});
    };

    return (
        <form onSubmit={handleSubmit}>
            <h5 className="mb-3">{id > 0 ? 'Editar producto' : 'Nuevo producto'}</h5>

            <div className="mb-3">
                <input
                    placeholder="Nombre"
                    className={`form-control ${errors.name ? 'is-invalid' : ''}`}
                    name="name"
                    value={name}
                    onChange={handleChange}
                />
                {errors.name && <div className="invalid-feedback">{errors.name}</div>}
            </div>

            <div className="mb-3">
                <input
                    placeholder="Descripción"
                    className={`form-control ${errors.description ? 'is-invalid' : ''}`}
                    name="description"
                    value={description}
                    onChange={handleChange}
                />
                {errors.description && <div className="invalid-feedback">{errors.description}</div>}
            </div>

            <div className="mb-3">
                <input
                    placeholder="Precio"
                    type="number"
                    className={`form-control ${errors.price ? 'is-invalid' : ''}`}
                    name="price"
                    value={price}
                    onChange={handleChange}
                />
                {errors.price && <div className="invalid-feedback">{errors.price}</div>}
            </div>

            <button className={`btn ${id > 0 ? 'btn-warning' : 'btn-primary'}`} type="submit">
                {id > 0 ? 'Actualizar' : 'Crear'}
            </button>

            {id > 0 && (
                <button
                    type="button"
                    className="btn btn-secondary ms-2"
                    onClick={() => setForm(initialForm)}
                >
                    Cancelar
                </button>
            )}
        </form>
    );
};

ProductForm.propTypes = {
    handlerAdd: PropTypes.func.isRequired,
    productSelected: PropTypes.object.isRequired,
};
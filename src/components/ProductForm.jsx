import PropTypes from "prop-types";
import { useEffect, useState } from "react";
import { PriceTag } from "./PriceTag";

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

    useEffect(() => {
        setForm(productSelected);
        setErrors({});
    }, [productSelected]);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
        setErrors({ ...errors, [e.target.name]: '' });
    };

    const validate = () => {
        const e = {};
        if (!name.trim()) e.name = 'Escribe el nombre del producto';
        if (!description.trim()) e.description = 'Escribe una descripción';
        if (!price || isNaN(price) || Number(price) <= 0) e.price = 'Ingresa un precio mayor que cero';
        return e;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const validationErrors = validate();
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }
        const saved = await handlerAdd({ ...form, name: name.trim(), description: description.trim(), price: Number(price) });
        if (saved) {
            setForm(initialForm);
            setErrors({});
        }
    };

    const handleCancel = () => {
        setForm(initialForm);
        setErrors({});
    };

    return (
        <form className="panel" onSubmit={handleSubmit}>
            <h2 className="panel-title">{id > 0 ? 'Editar producto' : 'Nuevo producto'}</h2>

            <div className="label-preview" aria-hidden="true">
                <p className="label-preview-caption">Así se verá</p>
                <p className={`label-preview-name ${name ? '' : 'is-placeholder'}`}>{name || 'Nombre del producto'}</p>
                <p className={`label-preview-description ${description ? '' : 'is-placeholder'}`}>{description || 'Descripción'}</p>
                <PriceTag value={price} />
            </div>

            <div className="field">
                <label htmlFor="product-name">Nombre</label>
                <input
                    id="product-name"
                    name="name"
                    placeholder="Teclado mecánico"
                    value={name}
                    onChange={handleChange}
                    aria-invalid={errors.name ? 'true' : 'false'}
                    aria-describedby={errors.name ? 'product-name-error' : undefined}
                />
                {errors.name && <p id="product-name-error" className="field-error">{errors.name}</p>}
            </div>

            <div className="field">
                <label htmlFor="product-description">Descripción</label>
                <input
                    id="product-description"
                    name="description"
                    placeholder="Switches rojos, retroiluminado"
                    value={description}
                    onChange={handleChange}
                    aria-invalid={errors.description ? 'true' : 'false'}
                    aria-describedby={errors.description ? 'product-description-error' : undefined}
                />
                {errors.description && <p id="product-description-error" className="field-error">{errors.description}</p>}
            </div>

            <div className="field">
                <label htmlFor="product-price">Precio en pesos</label>
                <input
                    id="product-price"
                    name="price"
                    type="number"
                    inputMode="numeric"
                    placeholder="150000"
                    value={price}
                    onChange={handleChange}
                    aria-invalid={errors.price ? 'true' : 'false'}
                    aria-describedby={errors.price ? 'product-price-error' : undefined}
                />
                {errors.price && <p id="product-price-error" className="field-error">{errors.price}</p>}
            </div>

            <div className="form-actions">
                <button className="button button-primary" type="submit">
                    {id > 0 ? 'Guardar cambios' : 'Crear producto'}
                </button>

                {id > 0 && (
                    <button type="button" className="button button-ghost" onClick={handleCancel}>
                        Cancelar
                    </button>
                )}
            </div>
        </form>
    );
};

ProductForm.propTypes = {
    handlerAdd: PropTypes.func.isRequired,
    productSelected: PropTypes.object.isRequired,
};

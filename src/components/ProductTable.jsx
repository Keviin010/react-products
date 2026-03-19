import PropTypes from "prop-types";

export const ProductTable = ({ products, handlerProductSelected, handlerRemoveProduct }) => {

    const formatPrice = (price) =>
        new Intl.NumberFormat('es-CO', {
            style: 'currency',
            currency: 'COP',
            maximumFractionDigits: 0
        }).format(price);

    return (
        <table className="table table-hover table-striped align-middle">
            <thead className="table-dark">
                <tr>
                    <th>#</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Precio</th>
                    <th colSpan={2} className="text-center">Acciones</th>
                </tr>
            </thead>
            <tbody>
                {products.map(product => (
                    <tr key={product.id}>
                        <td className="text-muted">{product.id}</td>
                        <td className="fw-semibold">{product.name}</td>
                        <td>{product.description}</td>
                        <td className="text-primary fw-bold">{formatPrice(product.price)}</td>
                        <td>
                            <button
                                className="btn btn-sm btn-outline-warning"
                                onClick={() => handlerProductSelected(product)}
                            >
                                Editar
                            </button>
                        </td>
                        <td>
                            <button
                                className="btn btn-sm btn-outline-danger"
                                onClick={() => handlerRemoveProduct(product.id)}
                            >
                                Eliminar
                            </button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

ProductTable.propTypes = {
    products: PropTypes.array.isRequired,
    handlerProductSelected: PropTypes.func.isRequired,
    handlerRemoveProduct: PropTypes.func.isRequired,
};
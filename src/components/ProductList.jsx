import PropTypes from 'prop-types';
import { PriceTag } from './PriceTag';

export const ProductList = ({ products, selectedId, savedId, handlerProductSelected, handlerRemoveProduct }) => (
    <ul className="product-list">
        {products.map((product) => (
            <li
                key={product.id}
                className={`product ${product.id === selectedId ? 'is-selected' : ''} ${product.id === savedId ? 'is-saved' : ''}`}
            >
                <div className="product-info">
                    <p className="product-name">{product.name}</p>
                    <p className="product-description">{product.description}</p>
                </div>

                <PriceTag value={product.price} />

                <div className="product-actions">
                    <button
                        className="button button-ghost button-small"
                        aria-label={`Editar ${product.name}`}
                        onClick={() => handlerProductSelected(product)}
                    >
                        Editar
                    </button>
                    <button
                        className="button button-danger button-small"
                        aria-label={`Eliminar ${product.name}`}
                        onClick={() => handlerRemoveProduct(product.id)}
                    >
                        Eliminar
                    </button>
                </div>
            </li>
        ))}
    </ul>
);

ProductList.propTypes = {
    products: PropTypes.array.isRequired,
    selectedId: PropTypes.number.isRequired,
    savedId: PropTypes.number,
    handlerProductSelected: PropTypes.func.isRequired,
    handlerRemoveProduct: PropTypes.func.isRequired,
};

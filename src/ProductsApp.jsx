import PropTypes from 'prop-types';
import { useEffect, useState } from 'react';
import { ProductTable } from './components/ProductTable';
import { ProductForm } from './components/ProductForm';
import { create, findAll, remove, update } from './services/productService';
import Swal from 'sweetalert2';

const initialSelected = { id: 0, name: '', description: '', price: '' };

export const ProductsApp = ({ title = 'Gestión de Productos' }) => {

    const [products, setProducts] = useState([]);
    const [productSelected, setProductSelected] = useState(initialSelected);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Cargo los productos al montar el componente
    const getProducts = async () => {
        try {
            setLoading(true);
            setError(null);
            const result = await findAll();
            setProducts(result.data);
        } catch (err) {
            setError('No se pudo conectar con el servidor. Verifica que el backend esté corriendo.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        getProducts();
    }, []);

    const handlerAddProduct = async (product) => {
        try {
            if (product.id > 0) {
                const response = await update(product);
                setProducts(products.map(p => p.id === product.id ? response.data : p));
                Swal.fire({
                    title: 'Actualizado!',
                    text: `"${product.name}" fue actualizado con éxito.`,
                    icon: 'success',
                    timer: 2000,
                    showConfirmButton: false,
                });
            } else {
                const response = await create(product);
                setProducts([...products, response.data]);
                Swal.fire({
                    title: 'Creado!',
                    text: `"${product.name}" fue creado con éxito.`,
                    icon: 'success',
                    timer: 2000,
                    showConfirmButton: false,
                });
            }
            setProductSelected(initialSelected);
        } catch (err) {
            Swal.fire('Error', 'No se pudo guardar el producto.', 'error');
            console.error(err);
        }
    };

    const handlerProductSelected = (product) => {
        setProductSelected({ ...product });
    };

    const handlerRemoveProduct = (id) => {
        Swal.fire({
            title: '¿Eliminar producto?',
            text: 'Esta acción no se puede deshacer.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#6c757d',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar',
        }).then(async (result) => {
            if (result.isConfirmed) {
                try {
                    await remove(id);
                    setProducts(products.filter(p => p.id !== id));
                    Swal.fire({
                        title: 'Eliminado!',
                        icon: 'success',
                        timer: 1500,
                        showConfirmButton: false,
                    });
                } catch (err) {
                    Swal.fire('Error', 'No se pudo eliminar el producto.', 'error');
                    console.error(err);
                }
            }
        });
    };

    return (
        <div className="container my-4">
            <div className="d-flex justify-content-between align-items-center border-bottom pb-3 mb-4">
                <h2 className="mb-0">{title}</h2>
                <span className="badge bg-secondary fs-6">{products.length} productos</span>
            </div>

            <div className="row g-4">
                <div className="col-12 col-md-4">
                    <div className="card shadow-sm p-3">
                        <ProductForm
                            handlerAdd={handlerAddProduct}
                            productSelected={productSelected}
                        />
                    </div>
                </div>

                <div className="col-12 col-md-8">
                    {loading && (
                        <div className="text-center mt-4">
                            <div className="spinner-border text-primary" role="status" />
                            <p className="mt-2 text-muted">Cargando productos...</p>
                        </div>
                    )}

                    {error && (
                        <div className="alert alert-danger d-flex justify-content-between align-items-center">
                            {error}
                            <button className="btn btn-sm btn-outline-danger" onClick={getProducts}>
                                Reintentar
                            </button>
                        </div>
                    )}

                    {!loading && !error && products.length === 0 && (
                        <div className="alert alert-warning">No hay productos registrados.</div>
                    )}

                    {!loading && !error && products.length > 0 && (
                        <ProductTable
                            products={products}
                            handlerProductSelected={handlerProductSelected}
                            handlerRemoveProduct={handlerRemoveProduct}
                        />
                    )}
                </div>
            </div>
        </div>
    );
};

ProductsApp.propTypes = {
    title: PropTypes.string.isRequired,
};
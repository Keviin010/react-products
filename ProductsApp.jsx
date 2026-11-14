import PropTypes from 'prop-types';
import { useEffect, useState } from 'react';
import Swal from 'sweetalert2';
import { ProductList } from './components/ProductList';
import { ProductForm } from './components/ProductForm';
import { create, findAll, remove, update } from './services/productService';
import { MAX_ATTEMPTS } from './config';

const initialSelected = { id: 0, name: '', description: '', price: '' };

const Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 2200,
    timerProgressBar: true,
});

export const ProductsApp = ({ title = 'Gestión de Productos' }) => {

    const [products, setProducts] = useState([]);
    const [productSelected, setProductSelected] = useState(initialSelected);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);
    const [attempt, setAttempt] = useState(1);
    const [search, setSearch] = useState('');
    const [savedId, setSavedId] = useState(null);

    const getProducts = async () => {
        setLoading(true);
        setError(false);
        for (let current = 1; current <= MAX_ATTEMPTS; current++) {
            setAttempt(current);
            try {
                const result = await findAll();
                setProducts(result.data);
                setLoading(false);
                return;
            } catch (err) {
                console.error(err);
                if (current < MAX_ATTEMPTS) {
                    await new Promise((resolve) => setTimeout(resolve, 4000));
                }
            }
        }
        setError(true);
        setLoading(false);
    };

    useEffect(() => {
        getProducts();
    }, []);

    useEffect(() => {
        if (savedId === null) return;
        const timer = setTimeout(() => setSavedId(null), 1800);
        return () => clearTimeout(timer);
    }, [savedId]);

    const handlerAddProduct = async (product) => {
        try {
            if (product.id > 0) {
                const response = await update(product);
                setProducts((current) => current.map((p) => (p.id === product.id ? response.data : p)));
                setSavedId(product.id);
                Toast.fire({ icon: 'success', title: 'Cambios guardados' });
            } else {
                const response = await create(product);
                setProducts((current) => [...current, response.data]);
                setSavedId(response.data.id);
                Toast.fire({ icon: 'success', title: 'Producto creado' });
            }
            setProductSelected({ ...initialSelected });
            return true;
        } catch (err) {
            Swal.fire({
                icon: 'error',
                title: 'No se pudo guardar',
                text: 'Revisa tu conexión e inténtalo de nuevo. Lo que escribiste sigue en el formulario.',
                confirmButtonColor: '#0F7B63',
            });
            console.error(err);
            return false;
        }
    };

    const handlerProductSelected = (product) => {
        setProductSelected({ ...product });
        if (window.innerWidth < 960) {
            window.scrollTo({ top: 0, behavior: 'smooth' });
        }
    };

    const handlerRemoveProduct = (id) => {
        const product = products.find((p) => p.id === id);
        Swal.fire({
            title: '¿Eliminar producto?',
            text: `Se eliminará "${product.name}". Esta acción no se puede deshacer.`,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#B3372B',
            cancelButtonColor: '#5A6E68',
            confirmButtonText: 'Eliminar',
            cancelButtonText: 'Cancelar',
        }).then(async (result) => {
            if (result.isConfirmed) {
                try {
                    await remove(id);
                    setProducts((current) => current.filter((p) => p.id !== id));
                    if (productSelected.id === id) {
                        setProductSelected({ ...initialSelected });
                    }
                    Toast.fire({ icon: 'success', title: 'Producto eliminado' });
                } catch (err) {
                    Swal.fire({
                        icon: 'error',
                        title: 'No se pudo eliminar',
                        text: 'Revisa tu conexión e inténtalo de nuevo.',
                        confirmButtonColor: '#0F7B63',
                    });
                    console.error(err);
                }
            }
        });
    };

    const filteredProducts = products.filter((p) =>
        p.name.toLowerCase().includes(search.trim().toLowerCase())
    );

    const loaded = !loading && !error;

    return (
        <>
            <header className="topbar">
                <div className="wrap topbar-inner">
                    <div>
                        <h1 className="topbar-title">{title}</h1>
                    </div>
                </div>
            </header>

            <main className="wrap main">
                <div className="layout">
                    <ProductForm
                        handlerAdd={handlerAddProduct}
                        productSelected={productSelected}
                    />

                    <section aria-labelledby="catalog-title">
                        <div className="list-header">
                            <div>
                                <h2 id="catalog-title" className="list-title">Catálogo</h2>
                                {loaded && (
                                    <p className="list-count">
                                        {products.length} {products.length === 1 ? 'producto' : 'productos'}
                                    </p>
                                )}
                            </div>

                            {loaded && products.length > 0 && (
                                <input
                                    type="search"
                                    className="search"
                                    placeholder="Buscar por nombre"
                                    aria-label="Buscar por nombre"
                                    value={search}
                                    onChange={(e) => setSearch(e.target.value)}
                                />
                            )}
                        </div>

                        {loading && (
                            <div className="state" role="status">
                                <div className="spinner" />
                                <p className="state-title">Conectando con el servidor…</p>
                                <p className="state-text">Si estaba dormido, puede tardar hasta un minuto en despertar.</p>
                                {attempt > 1 && (
                                    <p className="state-text">Intento {attempt} de {MAX_ATTEMPTS}</p>
                                )}
                            </div>
                        )}

                        {error && (
                            <div className="state state-error" role="alert">
                                <p className="state-title">No pudimos conectar con el servidor</p>
                                <p className="state-text">Revisa que el backend esté corriendo y vuelve a intentarlo.</p>
                                <button className="button button-primary" onClick={getProducts}>
                                    Reintentar
                                </button>
                            </div>
                        )}

                        {loaded && products.length === 0 && (
                            <div className="state state-empty">
                                <p className="state-title">Todavía no hay productos</p>
                                <p className="state-text">Crea el primero con el formulario.</p>
                            </div>
                        )}

                        {loaded && products.length > 0 && filteredProducts.length === 0 && (
                            <div className="state state-empty">
                                <p className="state-title">Ningún producto coincide con «{search.trim()}»</p>
                                <p className="state-text">Prueba con otro nombre.</p>
                            </div>
                        )}

                        {loaded && filteredProducts.length > 0 && (
                            <ProductList
                                products={filteredProducts}
                                selectedId={productSelected.id}
                                savedId={savedId}
                                handlerProductSelected={handlerProductSelected}
                                handlerRemoveProduct={handlerRemoveProduct}
                            />
                        )}
                    </section>
                </div>
            </main>
        </>
    );
};

ProductsApp.propTypes = {
    title: PropTypes.string.isRequired,
};

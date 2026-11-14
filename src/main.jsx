import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import '@fontsource-variable/familjen-grotesk';
import '@fontsource/barlow-condensed/700.css';
import './index.css';
import { ProductsApp } from './ProductsApp.jsx';

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <ProductsApp title="Gestión de Productos" />
    </StrictMode>,
);

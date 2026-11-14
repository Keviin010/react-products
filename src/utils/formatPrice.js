const formatter = new Intl.NumberFormat('es-CO', {
    style: 'currency',
    currency: 'COP',
    maximumFractionDigits: 0,
});

export const formatPrice = (price) => formatter.format(Number(price) || 0);

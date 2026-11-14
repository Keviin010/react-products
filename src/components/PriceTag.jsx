import PropTypes from 'prop-types';
import { formatPrice } from '../utils/formatPrice';

export const PriceTag = ({ value }) => (
    <span className="price-tag">{formatPrice(value)}</span>
);

PriceTag.propTypes = {
    value: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
};

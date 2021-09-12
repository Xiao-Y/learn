// 全局过滤器

/**
 * 金额格式化
 * @param price
 * @returns {string}
 */
export function priceFormat(price) {
    return (price / 100).toFixed(2);
}


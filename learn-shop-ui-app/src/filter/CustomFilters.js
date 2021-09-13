// 全局过滤器

/**
 * 金额格式化
 * @param price
 * @returns {string}
 */
export function priceFormat(price) {
    return (price / 100).toFixed(2);
}

/**
 * 金额格式化(带样式的)
 * @param price
 * @returns {string}
 */
export function priceFormatStyle(price) {
    let s = (price / 100).toFixed(2);
    let split = s.split(".");
    let unit = "<span class='unit' style='font-size: 12px;color: #fc603a;font-weight: bold;'>￥</span>";
    let integer = "<span class='integer' style='font-size: 17px;color: #fc603a;font-weight: bold;'>" + split[0] + "</span>";
    let decimal = "<span class='decimal' style='font-size: 10px;color: #fc603a;font-weight: bold;'>" + split[1] + "</span>";
    return (unit + integer + "." + decimal);
}


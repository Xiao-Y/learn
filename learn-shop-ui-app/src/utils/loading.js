import {Toast} from 'vant';

let needLoadingRequestCount = 0;

let loading;

function startLoading() {
    loading = Toast.loading({
        mask: true,
        message: "加载中..."
    });
}

function endLoading() {
    loading.clear();
}

/**
 * 显示loading
 */
export function showFullScreenLoading() {
    if (needLoadingRequestCount === 0) {
        startLoading();
    }
    needLoadingRequestCount++
}

/**
 * 隐藏loading
 */
export function tryHideFullScreenLoading() {
    if (needLoadingRequestCount <= 0) return;
    needLoadingRequestCount--;
    if (needLoadingRequestCount === 0) {
        endLoading();
    }
}

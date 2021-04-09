package com.gzz.stateMachine.event;

public enum OrderTransitionEvent {
	NONE("占位，无实际用处"),
	CREATE_ORDER("订单创建"),
	MEMBER_CANCEL("用户取消"),
	AUTO_CANCEL("自动取消"),
	CLIENT_PAY("客户端支付确认"),
	SERVER_PAY_NOTIFY("服务端支付确认"),
	SYSTEM_REFUND("自动退款"),
	VENDOR_ACCEPT("商家接单"),
	MEMBER_REFUND("用户退单"),
	MEMBER_REFUND_CONFIRM_BY_VENDOR("商家同意退款"),
	// AUTO_REFUND_CONFIRM("自动同意退款"), -- 同商家确认
	REFUND_RESULT_NOTIFY("计费退单结果通知"),
	/*********外卖事件 begin***************/
	PUBLISH_DELIVERY_TO_FN("商家发单(蜂鸟)"),
	PUBLISH_DELIVERY_BY_VENDOR_OR_SELF("商家发单(自送/自提)"),
	DELIVERY_REJECT("配送拒单"),
	DELIVERY_ACCEPT("外卖接单"),
	DELIVERY_UPDATE("外卖状态更新"),
	DELIVERY_COMPLETE("已送达"),
	DELIVERY_CANCEL("配送取消"),
	DELIVERY_TIMEOUT("接单超时"),
	/*********外卖事件 end***************/
	APPLY_VERIFY_CODE("发起核销码申请服务"),
	ORDER_CONFIRM("订单确认"),
	VENDOR_CANCEL("商家取消"),
	VENDOR_COMPLAINT("商家投诉"),
	VENDOR_COMPLAINT_END("商家申诉处理完成"),
	TO_SETTLEMENT("维保和退款订单自动转待结算状态"),
	SETTLEMENT("订单结算"),
	ORDER_COMMENT("订单评论标识")
	;
	
	private String note;
	
	private OrderTransitionEvent(String note) {
		setNote(note);
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}

package com.gzz.stateMachine.entity;
/**
 * 退单时订单状态照常流转
 * 1 更新ES时通过退单标识确定前端显示状态
 * 2 退单撤销时重新显示订单状态
 * @author niwenjie
 *
 */
public enum OrderStatus {
	NONE("初始状态"),
	OS00("待支付"),
	OS01("用户取消"),
	OS02("超时取消"),
	OS10("用户已支付，后台待确认"),
	OS20("支付已确认，待配货"),
	OS30("已接单"),
	OS40("已发起服务"),
	// OS41("服务已接受：如骑手已接单"), 一旦骑手接单 状态便进入服务中，服务的状态有服务自行控制
	OS42("服务进行中：如骑手到店，配送中等"),
	OS47("服务拒绝"),
	OS48("服务取消"),
	OS49("服务完成：如已送达"),
	OS50("已签收,维保中"),
	OS60("已退单"),
	OS70("订单异常处理中"),
	OS79("商家申诉中"),
	OS80("退款完成，订单关闭"),
	OS90("订单完成，订单关闭"),
	;
	private String note;
	
	OrderStatus(String note) {
		this.setNote(note);
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}

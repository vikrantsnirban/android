package net.versatile.android.blocker.service;

import java.lang.reflect.Method;
import java.util.List;

import net.versatile.android.blocker.dto.Rule;
import net.versatile.android.blocker.utils.RuleUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

public class CallHandler extends BroadcastReceiver{
	String TAG = "CallHandler";

	@Override
	public void onReceive(Context context, Intent intent) {
		String extraState =intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		if (extraState.equals(TelephonyManager.EXTRA_STATE_RINGING)){ 

			String incomingNumber =intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
			Log.d(TAG,"Call from:" + incomingNumber);
			
			List<Rule> rules = new RulesDataManager(context).getAllRules();
			
			Boolean handleCall = false;
			
			if(rules != null)
			for(Rule rule: rules){
				if(rule.getRuleCriteria().contains("Cotains") && incomingNumber.contains(rule.getRuleExpression())){
					handleCall = true;
					break;
				}
				
				if(rule.getRuleCriteria().contains("Start With") && incomingNumber.startsWith(rule.getRuleExpression())){
					handleCall = true;
					break;
				}
				
				if(rule.getRuleCriteria().contains("End With") && incomingNumber.endsWith(rule.getRuleExpression())){
					handleCall = true;
					break;
				}
				
				if(rule.getRuleCriteria().contains("Format") && incomingNumber.replace("+","").matches(RuleUtils.getRegexByFormat(rule.getRuleExpression()))){
					handleCall = true;
					break;
				}
			}
			
			if(handleCall){
				TelephonyManager telephony = (TelephonyManager) 
						  context.getSystemService(Context.TELEPHONY_SERVICE);  
						  try {
						   Class c = Class.forName(telephony.getClass().getName());
						   Method m = c.getDeclaredMethod("getITelephony");
						   m.setAccessible(true);
						   ITelephony  telephonyService = (ITelephony) m.invoke(telephony);
						   telephonyService.endCall();
						  } catch (Exception e) {
						   e.printStackTrace();
						  }
			}

		}
	}

}

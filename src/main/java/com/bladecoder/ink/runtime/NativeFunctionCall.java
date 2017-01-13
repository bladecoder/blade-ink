package com.bladecoder.ink.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NativeFunctionCall extends RTObject {
	static interface BinaryOp {
		Object invoke(Object left, Object right);
	}

	static interface UnaryOp {
		Object invoke(Object val);
	}

	public static final String Add = "+";
	public static final String And = "&&";
	public static final String Divide = "/";
	public static final String Equal = "==";
	public static final String Greater = ">";
	public static final String GreaterThanOrEquals = ">=";
	public static final String Less = "<";
	public static final String LessThanOrEquals = "<=";
	public static final String Max = "MAX";
	public static final String Min = "MIN";

	public static final String Mod = "%";
	public static final String Multiply = "*";
	private static HashMap<String, NativeFunctionCall> nativeFunctions;
	public static final String Negate = "_"; // distinguish from "-" for
												// subtraction
	public static final String Not = "!";

	public static final String Has = "?";
	public static final String Invert = "~";
	public static final String Intersect = "^";

	public static final String SetMax = "SET_MAX";
	public static final String SetMin = "SET_MIN";
	public static final String All = "SET_ALL";
	public static final String Count = "SET_COUNT";

	public static final String NotEquals = "!=";

	public static final String Or = "||";

	public static final String Subtract = "-";

	static void addSetBinaryOp(String name, BinaryOp op) {
		addOpToNativeFunc(name, 2, ValueType.Set, op);
	}

	static void addSetUnaryOp(String name, UnaryOp op) {
		addOpToNativeFunc(name, 1, ValueType.Set, op);
	}

	static void addFloatBinaryOp(String name, BinaryOp op) {
		addOpToNativeFunc(name, 2, ValueType.Float, op);
	}

	static void addFloatUnaryOp(String name, UnaryOp op) {
		addOpToNativeFunc(name, 1, ValueType.Float, op);
	}

	static void addIntBinaryOp(String name, BinaryOp op) {
		addOpToNativeFunc(name, 2, ValueType.Int, op);
	}

	static void addIntUnaryOp(String name, UnaryOp op) {
		addOpToNativeFunc(name, 1, ValueType.Int, op);
	}

	static void addOpToNativeFunc(String name, int args, ValueType valType, Object op) {
		NativeFunctionCall nativeFunc = nativeFunctions.get(name);

		// Operations for each data type, for a single operation (e.g. "+")

		if (nativeFunc == null) {
			nativeFunc = new NativeFunctionCall(name, args);
			nativeFunctions.put(name, nativeFunc);
		}

		nativeFunc.addOpFuncForType(valType, op);
	}

	static void addStringBinaryOp(String name, BinaryOp op) {
		addOpToNativeFunc(name, 2, ValueType.String, op);
	}

	public static boolean callExistsWithName(String functionName) {
		generateNativeFunctionsIfNecessary();
		return nativeFunctions.containsKey(functionName);
	}

	public static NativeFunctionCall callWithName(String functionName) {
		return new NativeFunctionCall(functionName);
	}

	static void generateNativeFunctionsIfNecessary() {
		if (nativeFunctions == null) {
			nativeFunctions = new HashMap<String, NativeFunctionCall>();

			// Int operations
			addIntBinaryOp(Add, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Integer) left + (Integer) right;
				}
			});

			addIntBinaryOp(Subtract, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Integer) left - (Integer) right;
				}
			});

			addIntBinaryOp(Multiply, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Integer) left * (Integer) right;
				}
			});

			addIntBinaryOp(Divide, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Integer) left / (Integer) right;
				}
			});

			addIntBinaryOp(Mod, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Integer) left % (Integer) right;
				}
			});

			addIntUnaryOp(Negate, new UnaryOp() {

				@Override
				public Object invoke(Object val) {
					return -(Integer) val;
				}
			});

			addIntBinaryOp(Equal, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Integer) left == (Integer) right ? 1 : 0;
				}
			});

			addIntBinaryOp(Greater, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Integer) left > (Integer) right ? 1 : 0;
				}
			});

			addIntBinaryOp(Less, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Integer) left < (Integer) right ? 1 : 0;
				}
			});
			addIntBinaryOp(GreaterThanOrEquals, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Integer) left >= (Integer) right ? 1 : 0;
				}
			});
			addIntBinaryOp(LessThanOrEquals, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Integer) left <= (Integer) right ? 1 : 0;
				}
			});
			addIntBinaryOp(NotEquals, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Integer) left != (Integer) right ? 1 : 0;
				}
			});

			addIntUnaryOp(Not, new UnaryOp() {

				@Override
				public Object invoke(Object val) {
					return (Integer) val == 0 ? 1 : 0;
				}
			});

			addIntBinaryOp(And, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Integer) left != 0 && (Integer) right != 0 ? 1 : 0;
				}
			});
			addIntBinaryOp(Or, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Integer) left != 0 || (Integer) right != 0 ? 1 : 0;
				}
			});
			addIntBinaryOp(Max, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return Math.max((Integer) left, (Integer) right);
				}
			});
			addIntBinaryOp(Min, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return Math.min((Integer) left, (Integer) right);
				}
			});

			// Float operations
			addFloatBinaryOp(Add, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Float) left + (Float) right;
				}
			});

			addFloatBinaryOp(Subtract, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Float) left - (Float) right;
				}
			});

			addFloatBinaryOp(Multiply, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Float) left * (Float) right;
				}
			});

			addFloatBinaryOp(Divide, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Float) left / (Float) right;
				}
			});

			addFloatBinaryOp(Mod, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Float) left % (Float) right;
				}
			});

			addFloatUnaryOp(Negate, new UnaryOp() {

				@Override
				public Object invoke(Object val) {
					return -(Float) val;
				}
			});

			addFloatBinaryOp(Equal, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Float) left == (Float) right ? (Integer) 1 : (Integer) 0;
				}
			});

			addFloatBinaryOp(Greater, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Float) left > (Float) right ? (Integer) 1 : (Integer) 0;
				}
			});

			addFloatBinaryOp(Less, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Float) left < (Float) right ? (Integer) 1 : (Integer) 0;
				}
			});
			addFloatBinaryOp(GreaterThanOrEquals, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Float) left >= (Float) right ? (Integer) 1 : (Integer) 0;
				}
			});
			addFloatBinaryOp(LessThanOrEquals, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Float) left <= (Float) right ? (Integer) 1 : (Integer) 0;
				}
			});
			addFloatBinaryOp(NotEquals, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Float) left != (Float) right ? (Integer) 1 : (Integer) 0;
				}
			});

			addFloatUnaryOp(Not, new UnaryOp() {

				@Override
				public Object invoke(Object val) {
					return (Float) val == 0 ? (Integer) 1 : (Integer) 0;
				}
			});

			addFloatBinaryOp(And, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Float) left != 0 && (Float) right != 0 ? (Integer) 1 : (Integer) 0;
				}
			});
			addFloatBinaryOp(Or, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (Float) left != 0 || (Float) right != 0 ? (Integer) 1 : (Integer) 0;
				}
			});
			addFloatBinaryOp(Max, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return Math.max((Float) left, (Float) right);
				}
			});
			addFloatBinaryOp(Min, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return Math.min((Float) left, (Float) right);
				}
			});

			// String operations
			addStringBinaryOp(Add, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (String) left + (String) right;
				}
			});
			// concat
			addStringBinaryOp(Equal, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return ((String) left).equals(right) ? (Integer) 1 : (Integer) 0;
				}
			});

			// Set operations
			addSetBinaryOp(Add, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return ((SetDictionary) left).union((SetDictionary) right);
				}
			});

			addSetBinaryOp(And, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return ((SetDictionary) left).union((SetDictionary) right);
				}
			});

			addSetBinaryOp(Subtract, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return ((SetDictionary) left).without((SetDictionary) right);
				}
			});

			addSetBinaryOp(Has, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return ((SetDictionary) left).contains((SetDictionary) right) ? (Integer) 1 : (Integer) 0;
				}
			});

			addSetBinaryOp(Intersect, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return ((SetDictionary) left).intersect((SetDictionary) right);
				}
			});

			addSetBinaryOp(Equal, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return ((SetDictionary) left).equals(right) ? (Integer) 1 : (Integer) 0;
				}
			});

			addSetBinaryOp(Greater, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return ((SetDictionary) left).size() > 0
							&& ((SetDictionary) left).greaterThan((SetDictionary) right) ? (Integer) 1 : (Integer) 0;
				}
			});

			addSetBinaryOp(Less, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return ((SetDictionary) left).lessThan((SetDictionary) right) ? (Integer) 1 : (Integer) 0;
				}
			});

			addSetBinaryOp(GreaterThanOrEquals, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return ((SetDictionary) left).size() > 0
							&& ((SetDictionary) left).greaterThanOrEquals((SetDictionary) right) ? (Integer) 1 : (Integer) 0;
				}
			});

			addSetBinaryOp(LessThanOrEquals, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return ((SetDictionary) left).size() > 0
							&& ((SetDictionary) left).lessThanOrEquals((SetDictionary) right) ? (Integer) 1 : (Integer) 0;
				}
			});

			addSetBinaryOp(NotEquals, new BinaryOp() {
				@Override
				public Object invoke(Object left, Object right) {
					return (!((SetDictionary) left).equals(right) ? (Integer) 1 : (Integer) 0);
				}
			});

			addSetUnaryOp(Not, new UnaryOp() {
				@Override
				public Object invoke(Object val) {
					return ((SetDictionary) val).size() == 0 ? (int) 1 : (int) 0;
				}
			});

			// Placeholder to ensure that Invert gets created at all,
			// since this function is never actually run, and is special cased
			// in Call
			addSetUnaryOp(Invert, new UnaryOp() {
				@Override
				public Object invoke(Object val) {
					return null;
				}
			});
			
			addSetUnaryOp(All, new UnaryOp() {
				@Override
				public Object invoke(Object val) {
					return null;
				}
			});

			addSetUnaryOp(SetMin, new UnaryOp() {
				@Override
				public Object invoke(Object val) {
					return ((SetDictionary) val).minAsSet();
				}
			});

			addSetUnaryOp(SetMax, new UnaryOp() {
				@Override
				public Object invoke(Object val) {
					return ((SetDictionary) val).maxAsSet();
				}
			});
			
			addSetUnaryOp(Count, new UnaryOp() {
				@Override
				public Object invoke(Object val) {
					return ((SetDictionary) val).size();
				}
			});

			BinaryOp divertTargetsEqual = new BinaryOp() {

				@Override
				public Object invoke(Object left, Object right) {
					return ((Path) left).equals((Path) left) ? (Integer) 1 : (Integer) 0;
				}

			};

			addOpToNativeFunc(Equal, 2, ValueType.DivertTarget, divertTargetsEqual);
		}

	}

	private String name;

	private int numberOfParameters;

	private boolean isPrototype;

	private HashMap<ValueType, Object> operationFuncs;

	private NativeFunctionCall prototype;

	// Require default constructor for serialisation
	public NativeFunctionCall() {
		generateNativeFunctionsIfNecessary();
	}

	public NativeFunctionCall(String name) {
		generateNativeFunctionsIfNecessary();
		this.setName(name);
	}

	// Only called internally to generate prototypes
	NativeFunctionCall(String name, int numberOfParamters) {
		isPrototype = true;
		this.setName(name);
		this.setNumberOfParameters(numberOfParamters);
	}

	void addOpFuncForType(ValueType valType, Object op) {
		if (operationFuncs == null) {
			operationFuncs = new HashMap<ValueType, Object>();
		}

		operationFuncs.put(valType, op);
	}

	public RTObject call(List<RTObject> parameters) throws Exception {

		if (prototype != null) {
			return prototype.call(parameters);
		}

		if (getNumberOfParameters() != parameters.size()) {
			throw new Exception("Unexpected number of parameters");
		}

		for (RTObject p : parameters) {
			if (p instanceof Void)
				throw new StoryException(
						"Attempting to perform operation on a void value. Did you forget to 'return' a value from a function you called here?");

		}

		// Special cases, where the functions require knowledge of the origin
		// set, not just the raw set dictionary
		if (parameters.size() == 1 && parameters.get(0) instanceof SetValue && Invert.equals(name)) {

			if (parameters.size() == 1 && parameters.get(0) instanceof SetValue) {

				if (name.equals(Invert)) {
					SetValue setValue = (SetValue) parameters.get(0);
					SetValue inv = setValue.getInverse();
					if (inv == null)
						return new SetValue("UNKNOWN", 0);
					return inv;
				} else if (name.equals(All)) {
					SetValue setValue = (SetValue) parameters.get(0);
					SetValue all = setValue.getAll();
					if (all == null)
						return new SetValue("UNKNOWN", 0);
					return all;
				}
			}
		}

		List<Value<?>> coercedParams = coerceValuesToSingleType(parameters);
		ValueType coercedType = coercedParams.get(0).getValueType();

		// Originally CallType gets a type parameter taht is used to do some
		// casting, but we can do without.
		if (coercedType == ValueType.Int) {
			return callType(coercedParams);
		} else if (coercedType == ValueType.Float) {
			return callType(coercedParams);
		} else if (coercedType == ValueType.String) {
			return callType(coercedParams);
		} else if (coercedType == ValueType.DivertTarget) {
			return callType(coercedParams);
		} else if (coercedType == ValueType.Set) {
			return callType(coercedParams);
		}

		return null;

	}

	Value<?> callSetIntOperation(List<RTObject> setIntParams) throws StoryException, Exception {
		SetValue setVal = (SetValue) setIntParams.get(0);
		IntValue intVal = (IntValue) setIntParams.get(1);

		List<RTObject> coercedInts = new ArrayList<RTObject>();

		coercedInts.add(new IntValue(setVal.maxItem().getValue()));
		coercedInts.add(intVal);

		IntValue intResult = (IntValue) call(coercedInts);

		String newItemName = null;

		Set originSet = setVal.singleOriginSet;
		if (originSet != null) {
			newItemName = originSet.getItemWithValue(intResult.value);

			if (newItemName != null)
				newItemName = originSet.getName() + "." + newItemName;
		}

		if (newItemName == null)
			newItemName = "UNKNOWN";

		return new SetValue(newItemName, intResult.value);
	}

	private RTObject callType(List<Value<?>> parametersOfSingleType) throws StoryException, Exception {

		Value<?> param1 = parametersOfSingleType.get(0);
		ValueType valType = param1.getValueType();
		Value<?> val1 = param1;

		int paramCount = parametersOfSingleType.size();

		if (paramCount == 2 || paramCount == 1) {
			Object opForTypeObj = operationFuncs.get(valType);

			if (opForTypeObj == null) {
				throw new StoryException("Cannot perform operation '" + this.getName() + "' on " + valType);
			}

			// Binary
			if (paramCount == 2) {
				Value<?> param2 = parametersOfSingleType.get(1);
				Value<?> val2 = param2;

				BinaryOp opForType = (BinaryOp) opForTypeObj;

				// Return value unknown until it's evaluated
				Object resultVal = opForType.invoke(val1.getValue(), val2.getValue());

				return AbstractValue.create(resultVal);
			} else { // Unary
				UnaryOp opForType = (UnaryOp) opForTypeObj;

				Object resultVal = opForType.invoke(val1.getValue());

				return AbstractValue.create(resultVal);
			}
		} else {
			throw new Exception(
					"Unexpected number of parameters to NativeFunctionCall: " + parametersOfSingleType.size());
		}
	}

	List<Value<?>> coerceValuesToSingleType(List<RTObject> parametersIn) throws Exception {
		ValueType valType = ValueType.Int;

		SetValue specialCaseSet = null;

		for (RTObject obj : parametersIn) {
			// Find out what the output type is
			// "higher level" types infect both so that binary operations
			// use the same type on both sides. e.g. binary operation of
			// int and float causes the int to be casted to a float.
			Value<?> val = (Value<?>) obj;
			if (val.getValueType().ordinal() > valType.ordinal()) {
				valType = val.getValueType();
			}

			if (val.getValueType() == ValueType.Set) {
				specialCaseSet = (SetValue) val;
			}
		}

		// // Coerce to this chosen type
		ArrayList<Value<?>> parametersOut = new ArrayList<Value<?>>();
		// for (RTObject p : parametersIn) {
		// Value<?> val = (Value<?>) p;
		// Value<?> castedValue = (Value<?>) val.cast(valType);
		// parametersOut.add(castedValue);

		// Special case: Coercing to Ints to Sets
		// We have to do it early when we have both parameters
		// to hand - so that we can make use of the Set's origin
		if (valType == ValueType.Set) {

			for (RTObject p : parametersIn) {
				Value<?> val = (Value<?>) p;
				if (val.getValueType() == ValueType.Set) {
					parametersOut.add(val);
				} else if (val.getValueType() == ValueType.Int) {
					int intVal = (int) val.getValueObject();
					Set set = specialCaseSet.singleOriginSet;
					if (set == null)
						throw new StoryException(
								"Cannot mix Set and Int values here because the existing Set appears to contain items from a mixture of different Set definitions. How do we know which Set is the Int referring to?");

					String itemName = set.getItemWithValue(intVal);

					if (itemName != null) {
						SetValue castedValue = new SetValue(set.getName() + "." + itemName, intVal);
						parametersOut.add(castedValue);
					} else
						throw new StoryException(
								"Could not find Set item with the value " + intVal + " in " + set.getName());
				} else
					throw new StoryException("Cannot mix Sets and " + val.getValueType() + " values in this operation");
			}

		}

		// Normal Coercing (with standard casting)
		else {
			for (RTObject p : parametersIn) {
				Value<?> val = (Value<?>) p;
				Value<?> castedValue = (Value<?>) val.cast(valType);
				parametersOut.add(castedValue);
			}
		}

		return parametersOut;
	}

	public String getName() {
		return name;
	}

	public int getNumberOfParameters() {
		if (prototype != null) {
			return prototype.getNumberOfParameters();
		} else {
			return numberOfParameters;
		}
	}

	public void setName(String value) {
		name = value;
		if (!isPrototype)
			prototype = nativeFunctions.get(name);

	}

	public void setNumberOfParameters(int value) {
		numberOfParameters = value;
	}

	@Override
	public String toString() {
		return "Native '" + getName() + "'";

	}
}

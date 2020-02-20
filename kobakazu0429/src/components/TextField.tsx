import React, { FC, useState, useCallback, useEffect } from "react";
import styled, { css } from "styled-components";
import { ValidationMethod, ValidationResult } from "@/utils/validation";

type TextInputType = "text" | "password" | "email";

export interface Props {
  value?: string;
  name?: string;
  placeholder?: string;
  type?: TextInputType;
  multiLine?: boolean;
  validations?: ValidationMethod[];
  onChange?(value: string): void;
}

export const TextField: FC<Props> = ({
  value,
  name,
  placeholder,
  multiLine,
  onChange,
  validations,
  type = "text"
}) => {
  const [currentInputValue, setCurrentInputValue] = useState("");
  const [validationErrors, setValidationErrors] = useState<ValidationResult[]>(
    []
  );
  const [inFocus, setInFocus] = useState(false);

  const handleChange = useCallback(
    (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
      const data = e.target.value;
      setCurrentInputValue(data);

      if (onChange) {
        onChange(data);
      }
    },
    []
  );

  useEffect(() => {
    if (validations) {
      if (!currentInputValue) return;

      const newErrors: ValidationResult[] = [];
      validations.forEach(validation => {
        newErrors.push(validation(currentInputValue));
      });
      setValidationErrors(newErrors);
    }
  }, [currentInputValue, inFocus]);

  const handleFocus = useCallback(() => {
    setInFocus(true);
  }, []);

  // call: did handleFoucus
  const handleBlur = useCallback(() => {
    setInFocus(false);
  }, []);

  useEffect(() => {
    if (value != null) {
      setCurrentInputValue(value);
    }
  }, [value]);

  const props = {
    type,
    placeholder,
    onFocus: handleFocus,
    onBlur: handleBlur,
    id: name,
    value: currentInputValue,
    onChange: handleChange,
    styledFocus: inFocus ? true : false,
    validationError:
      validationErrors.filter(v => v.errorMessage).length > 0 ? true : false
  };

  return (
    <div>
      {multiLine ? (
        <>
          <StyledTextarea {...props} rows={4} />
          {validationErrors.length > 0
            ? validationErrors.map((v, i) => (
                <span key={i}>{v.errorMessage}</span>
              ))
            : null}
        </>
      ) : (
        <>
          <StyledInput {...props} />
          {validationErrors.length > 0
            ? validationErrors.map((v, i) => (
                <span key={i}>{v.errorMessage}</span>
              ))
            : null}
        </>
      )}
    </div>
  );
};

const style = css<{ validationError: boolean }>`
  margin: 5px;
  margin-left: 0;
  margin-right: 0;
  padding: 5px;
  padding-left: 15px;
  line-height: 1.5rem;
  width: 100%;
  border: 2px solid
    ${({ theme, validationError }) =>
      validationError ? theme.color.error : theme.color.brand};
  border-radius: 1.5rem;
  ::placeholder {
    color: ${({ theme }) => theme.color.divider};
    opacity: 1;
  }
  ::-ms-input-placeholder {
    color: ${({ theme }) => theme.color.divider};
  }
  &:-webkit-autofill {
    box-shadow: 0 0 0 1000px #fff inset;
  }
`;

const StyledInput = styled.input`
  ${style};
`;

const StyledTextarea = styled.textarea`
  ${style}
`;

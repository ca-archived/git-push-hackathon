export const isDevelopment = () => {
  return process.env.NODE_ENV === "development";
};

export const useMockApi = () => {
  return process.env.USE_MOCK_API === "true";
};

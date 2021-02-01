import React from "react";
import { gql, useMutation } from "@apollo/client";
import { useSelector } from "react-redux";
import { showError, showNotification } from "../utils";
import { toast } from "react-toastify";

import Alert from "../commonComponents/Alert";

const uploadPatients = gql`
  mutation UploadPatients($patientList: Upload!) {
    uploadPatients(patientList: $patientList)
  }
`;

interface Props {
  onSuccess: () => void;
}

const PatientUpload = ({ onSuccess }: Props) => {
  const [upload] = useMutation(uploadPatients);

  const isGlobalAdmin = useSelector(
    (state) => (state as any)?.user?.isAdmin as boolean
  );
  if (!isGlobalAdmin) {
    return null;
  }

  const bulkUpload = async ({
    target: { files },
  }: React.ChangeEvent<HTMLInputElement>) => {
    const fileList = files;
    if (fileList === null) {
      showError(toast, "Error", "File not found");
      return;
    }
    upload({ variables: { patientList: fileList[0] } }).then((response) => {
      showNotification(
        toast,
        <Alert
          type="success"
          title={`Patients uploaded`}
          body={response.data.uploadPatients}
        />
      );
      onSuccess();
    });
  };

  return (
    <input
      type="file"
      name="file"
      placeholder="UploadCSV..."
      onChange={bulkUpload}
    />
  );
};

export default PatientUpload;

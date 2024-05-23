declare namespace API {
  type LoginUser = {
    username: string;
    password: string;
  };

  type LoginResult = {
    success: boolean;
    message: string;
  };

  type MachineState = {
    name: string;
    cpu: number;
    mem: number;
    disk: MachineDiskState[];
    last: string;
  };

  type MachineDiskState = {
    mount: string;
    usage: number;
  };
}

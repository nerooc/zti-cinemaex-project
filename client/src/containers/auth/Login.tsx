import React, { useState, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import axios from '../../utils/axios';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { toastConfig } from '../../constants/toastConfig';
import { FaChevronLeft } from 'react-icons/fa';
import { Form } from '../../components';

toast.configure();

interface Props {
  setAuth: (boolean: Boolean) => void;
  setRole: (role: string) => void;
  role: string;
}

const Login: React.FC<Props> = ({ setAuth, setRole, role }) => {
  let history = useHistory();

  const [inputData, setInputData] = useState({
    email: '',
    password: '',
  });

  const { email, password } = inputData;

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputData({
      ...inputData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    e.preventDefault();

    const body = { email, password };
    try {
      const { data } = await axios.post('/auth/login', body);

      if (data.accessToken) {
        localStorage.setItem('token', data.accessToken);
        setAuth(true);
        setRole(data.role);
        toast.success('Login successful!', toastConfig);
      }
    } catch (err) {
      console.log(err.response.data)
      const msg = err.response.data.message;
      toast.error(msg, toastConfig);
    }
  };

  const handleReturn = () => {
    history.push('/');
  };

  useEffect(() => {
    const listener = (event) => {
      if (event.code === 'Enter' || event.code === 'NumpadEnter') {
        handleSubmit(event);
      }
    };
    document.addEventListener('keydown', listener);
    return () => {
      document.removeEventListener('keydown', listener);
    };

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [inputData]);

  return (
    <Form>
      <Form.Wrapper>
        <Form.Header>Login</Form.Header>
        <Form.Input
          type="email"
          name="email"
          placeholder="E-mail"
          value={email}
          onChange={handleChange}
        />
        <Form.Input
          type="password"
          name="password"
          placeholder="Password"
          value={password}
          onChange={handleChange}
        />
        <Form.ButtonContainer>
          <Form.Button onClick={handleReturn}>
            <FaChevronLeft />
          </Form.Button>
          <Form.Button forward={true} onClick={handleSubmit}>
            LOG IN
          </Form.Button>
        </Form.ButtonContainer>
        <Form.Redirect>
          Not a member?&nbsp;
          <Form.RedirectLink to="/register">Sign up now!</Form.RedirectLink>
        </Form.Redirect>
      </Form.Wrapper>
    </Form>
  );
};

export default Login;

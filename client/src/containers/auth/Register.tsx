import React, { useState, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import axios from '../../utils/axios';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { toastConfig } from '../../constants/toastConfig';
import { Form } from '../../components';
import { FaChevronLeft } from 'react-icons/fa';

interface Props {
  setAuth: (boolean: Boolean) => void;
  setRole: (role: string) => void;
  role: string;
}

const Register: React.FC<Props> = ({ setAuth, setRole, role }) => {
  let history = useHistory();

  const [inputData, setInputData] = useState({
    username: '',
    email: '',
    password: '',
    name: '',
    surname: '',
    newsletter: false,
  });

  const { username, email, password, name, surname, newsletter } = inputData;

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value =
      e.target.type === 'checkbox' ? e.target.checked : e.target.value;

    setInputData({
      ...inputData,
      [e.target.name]: value,
    });
  };

  const handleSubmit = async (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    e.preventDefault();

    const body = { username, password, name, surname, email, newsletter };

    try {
      const { data } = await axios.post('/auth/register', body);
      if (data) {
        // localStorage.setItem('token', data.accessToken);
        // setAuth(true);
        // setRole(data.role);
        toast.success(data.message + ' You can now log in!', toastConfig);
      }
    } catch (err) {
      const msg = err.response.data.message;
      toast.error(msg, toastConfig);
    }
  };

  const handleReturn = () => {
    history.push('/');
  };

  /* Allows the user to use enter to submit data */
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
    <Form register>
      <Form.Wrapper>
        <Form.Header>Register</Form.Header>
        <Form.Input
          type="text"
          name="username"
          placeholder="Username"
          value={username}
          onChange={handleChange}
        />
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
        <Form.Input
          type="name"
          name="name"
          placeholder="Name"
          value={name}
          onChange={handleChange}
        />
        <Form.Input
          type="surname"
          name="surname"
          placeholder="Surname"
          value={surname}
          onChange={handleChange}
        />
        <label>
          <Form.Checkbox
            name="newsletter"
            checked={newsletter}
            onChange={handleChange}
          />
          Do you want to get the news?
        </label>
        <Form.ButtonContainer>
          <Form.Button onClick={handleReturn}>
            <FaChevronLeft />
          </Form.Button>
          <Form.Button forward onClick={handleSubmit}>
            SUBMIT
          </Form.Button>
        </Form.ButtonContainer>
        <Form.Redirect>
          Already have an account?&nbsp;
          <Form.RedirectLink to="/login">Log in!</Form.RedirectLink>
        </Form.Redirect>
      </Form.Wrapper>
    </Form>
  );
};

export default Register;
